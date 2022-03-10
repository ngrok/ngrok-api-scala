package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object HttpResponseBackends {
  private case class HttpResponseBackendsCreate(
    description: Option[String],
    metadata: Option[String],
    body: Option[String],
    headers: Option[Map[String, String]],
    statusCode: Option[Int]
  )

  private object HttpResponseBackendsCreate {
    implicit val encodeHttpResponseBackendsCreate: Encoder[HttpResponseBackendsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.body.map(_.asJson).map(("body", _)),
          value.headers.map(_.asJson).map(("headers", _)),
          value.statusCode.map(_.asJson).map(("status_code", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class HttpResponseBackendsUpdate(
    description: Option[String],
    metadata: Option[String],
    body: Option[String],
    headers: Option[Map[String, String]],
    statusCode: Option[Int]
  )

  private object HttpResponseBackendsUpdate {
    implicit val encodeHttpResponseBackendsUpdate: Encoder[HttpResponseBackendsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.body.map(_.asJson).map(("body", _)),
          value.headers.map(_.asJson).map(("headers", _)),
          value.statusCode.map(_.asJson).map(("status_code", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[HttpResponseBackends]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-http-response-backends">https://ngrok.com/docs/api#api-http-response-backends</a>.
  */
class HttpResponseBackends private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import HttpResponseBackends._

  /** Sends an API request for the Create operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-http-response-backends-create">https://ngrok.com/docs/api#api-http-response-backends-create</a>.
    *
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param body body to return as fixed content
    * @param headers headers to return
    * @param statusCode status code to return
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    body: Option[String] = None,
    headers: Option[Map[String, String]] = None,
    statusCode: Option[Int] = None
  ): Future[HttpResponseBackend] =
    apiClient.sendRequest[HttpResponseBackend](
      NgrokApiClient.HttpMethod.Post,
      "/backends/http_response",
      List.empty,
      Option(
        HttpResponseBackendsCreate(
          description,
          metadata,
          body,
          headers,
          statusCode
        ).asJson
      )
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-http-response-backends-delete">https://ngrok.com/docs/api#api-http-response-backends-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/backends/http_response/$id",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-http-response-backends-get">https://ngrok.com/docs/api#api-http-response-backends-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[HttpResponseBackend] =
    apiClient.sendRequest[HttpResponseBackend](
      NgrokApiClient.HttpMethod.Get,
      s"/backends/http_response/$id",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the List operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-http-response-backends-list">https://ngrok.com/docs/api#api-http-response-backends-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[HttpResponseBackendList]] =
    apiClient
      .sendRequest[HttpResponseBackendList](
        NgrokApiClient.HttpMethod.Get,
        "/backends/http_response",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Sends an API request for the Update operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-http-response-backends-update">https://ngrok.com/docs/api#api-http-response-backends-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param body body to return as fixed content
    * @param headers headers to return
    * @param statusCode status code to return
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    body: Option[String] = None,
    headers: Option[Map[String, String]] = None,
    statusCode: Option[Int] = None
  ): Future[HttpResponseBackend] =
    apiClient.sendRequest[HttpResponseBackend](
      NgrokApiClient.HttpMethod.Patch,
      s"/backends/http_response/$id",
      List.empty,
      Option(
        HttpResponseBackendsUpdate(
          description,
          metadata,
          body,
          headers,
          statusCode
        ).asJson
      )
    )

}
