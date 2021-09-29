package com.ngrok

import java.net.URI
import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.{Duration, OffsetDateTime}
import java.util

import cats.implicits._
import com.linecorp.armeria.client.{ClientFactory, WebClient, WebClientRequestPreparation}
import com.linecorp.armeria.common.{AggregatedHttpResponse, HttpHeaderNames, MediaType}
import com.ngrok.definitions.NgrokApiError
import com.ngrok.definitions.NgrokApiError._
import io.circe.parser._
import io.circe.{Decoder, DecodingFailure, Json, ParsingFailure}

import scala.collection.JavaConverters._
import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

/** Helpers for creating new [[DefaultNgrokApiClient]] instances. */
object DefaultNgrokApiClient {

  /** Creates a new default ngrok API client.
    *
    * @param apiKey API key used to access the ngrok API
    * @param httpClient an optional HTTP client to use to make requests
    * @param baseUri an optional API base URI
    * @return a new API client
    */
  def apply(apiKey: String, httpClient: Option[WebClient] = None, baseUri: Option[URI] = None): DefaultNgrokApiClient =
    new DefaultNgrokApiClient(
      apiKey,
      httpClient.getOrElse(newDefaultHttpClient),
      baseUri.getOrElse(NgrokApiClient.DefaultBaseUri)
    )

  /** Creates a new default Armeria [[WebClient]].
    *
    * @return a HTTP client
    */
  def newDefaultHttpClient: WebClient = {
    val factory = ClientFactory.builder
      .connectTimeout(Duration.ofSeconds(4))
      .idleTimeout(Duration.ofMinutes(2))
      .useHttp1Pipelining(false)
      .build()
    WebClient.builder
      .factory(factory)
      .responseTimeout(Duration.ofSeconds(30))
      .writeTimeout(Duration.ofSeconds(1))
      .build()
  }

  implicit private class HttpMethodExtensions(val method: NgrokApiClient.HttpMethod) extends AnyVal {
    import com.linecorp.armeria.common.{HttpMethod => ArmeriaHttpMethod}
    import ArmeriaHttpMethod._
    import NgrokApiClient.HttpMethod._
    def asArmeriaMethod: ArmeriaHttpMethod = method match {
      case Get    => GET
      case Post   => POST
      case Put    => PUT
      case Patch  => PATCH
      case Delete => DELETE
    }
  }

  private def queryParamToString(value: Any): String = value match {
    case s: String         => s
    case o: OffsetDateTime => o.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    case d: Duration       => d.getSeconds.toString
    case a                 => a.toString
  }

  private def handleDecodeFailure[T](response: AggregatedHttpResponse, error: io.circe.Error)(implicit
    cls: ClassTag[T]
  ): NgrokApiError = {
    val newCause = error match {
      case ParsingFailure(message, underlying) =>
        ParsingFailure(s"Failed to parse response body for type ${cls.runtimeClass.getName}: $message", underlying)
      case DecodingFailure(message, history) =>
        DecodingFailure(s"Failed to decode response body to type ${cls.runtimeClass.getName}: $message", history)
      case cause =>
        new RuntimeException(
          s"Failed to decode response body to type ${cls.runtimeClass.getName}: ${cause.getMessage}",
          cause
        )
    }
    NgrokApiError(response.status.code, Option(response.content).filter(!_.isEmpty).map(_.toStringUtf8), newCause)
  }
}

/** Default implementation of [[NgrokApiClient]] based on the Armeria HTTP client. */
class DefaultNgrokApiClient private (apiKey: String, httpClient: WebClient, baseUri: URI) extends NgrokApiClient {
  import DefaultNgrokApiClient._

  override def sendRequest[O: Decoder: ClassTag](uri: URI)(implicit ec: ExecutionContext): Future[O] = {
    val request = this.httpClient.prepare.get(uri.toString)
    sendRequest[O](request, None)
  }

  override def sendRequest[O: Decoder: ClassTag](
    method: NgrokApiClient.HttpMethod,
    endpoint: String,
    queryParams: List[(String, Option[Any])],
    body: Option[Json]
  )(implicit ec: ExecutionContext): Future[O] = {
    val request = this.httpClient.prepare
      .method(method.asArmeriaMethod)
      .path(s"${this.baseUri}$endpoint")

    val presentQueryParams = queryParams.flatMap {
      case (key, Some(value)) =>
        Option(new util.AbstractMap.SimpleEntry[String, String](key, queryParamToString(value)))
      case _ => None
    }
    if (presentQueryParams.nonEmpty) {
      request.queryParams(presentQueryParams.asJava)
    }

    sendRequest[O](request, body)
  }

  private def sendRequest[O: Decoder: ClassTag](
    request: WebClientRequestPreparation,
    body: Option[Json]
  )(implicit ec: ExecutionContext): Future[O] = {
    request
      .header(HttpHeaderNames.USER_AGENT, "ngrok-api-client-scala/" + Version.ClientVersion)
      .header("ngrok-version", Version.ApiVersion)
      .header(HttpHeaderNames.AUTHORIZATION, "Bearer " + this.apiKey)
    body.foreach(body => request.content(MediaType.JSON, body.noSpaces.getBytes(StandardCharsets.UTF_8)))

    request
      .execute()
      .aggregate()
      .toScala
      .flatMap(response =>
        Option(response.content)
          .filter(!_.isEmpty)
          .fold {
            if (response.status.isSuccess) {
              decode[O]("{}")
                .recoverWith { case _ => decode[O]("") }
                .leftMap(handleDecodeFailure(response, _))
                .fold(Future.failed, Future.successful)
            } else {
              Future.failed(NgrokApiError(response.status.code))
            }
          } { responseBody =>
            if (response.status.isSuccess) {
              decode[O](responseBody.toStringUtf8)
                .leftMap(handleDecodeFailure(response, _))
                .fold(Future.failed, Future.successful)
            } else {
              decode[NgrokApiError](responseBody.toStringUtf8)
                .leftMap(handleDecodeFailure(response, _))
                .fold(Future.failed, Future.failed)
            }
          }
      )
  }
}
