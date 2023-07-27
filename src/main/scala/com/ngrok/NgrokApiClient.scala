/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok

import java.io.Serializable
import java.net.URI

import io.circe.{Decoder, Json}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

/** Helpful constants and enums for making API requests. */
object NgrokApiClient {

  /** The default base URI for the public ngrok API server. */
  final val DefaultBaseUri = URI.create("https://api.ngrok.com")

  /** HTTP request methods. */
  sealed trait HttpMethod extends Product with Serializable
  object HttpMethod {

    /** HTTP GET */
    case object Get extends HttpMethod

    /** HTTP POST */
    case object Post extends HttpMethod

    /** HTTP PUT */
    case object Put extends HttpMethod

    /** HTTP PATCH */
    case object Patch extends HttpMethod

    /** HTTP DELETE */
    case object Delete extends HttpMethod
  }
}

/** API client interface.
  *
  * Concrete implementations can be written and passed to [[Ngrok]] on construction
  * in order to make HTTP requests or test business logic.
  */
trait NgrokApiClient {

  /** Sends a GET request directly to the specified URI.
    *
    * @param uri a URI to send the request to
    * @param ec an execution context
    * @tparam O the return type for the API response (must have an implicit [[io.circe.Decoder]] and [[scala.reflect.ClassTag]])
    * @return a future encapsulating the response type
    */
  def sendRequest[O: Decoder: ClassTag](uri: URI)(implicit ec: ExecutionContext): Future[O]

  /** Sends a request to the ngrok API.
    *
    * @param method the HTTP method to use
    * @param endpoint the endpoint (under the base URI) to send the request to
    * @param queryParams any query parameters to send along with the request (parameters with an empty
    *                    value will not be sent)
    * @param body JSON request body, if any
    * @param ec an execution context
    * @tparam O the return type for the API response (must have an implicit [[io.circe.Decoder]] and [[scala.reflect.ClassTag]])
    * @return a future encapsulating the response type
    */
  def sendRequest[O: Decoder: ClassTag](
    method: NgrokApiClient.HttpMethod,
    endpoint: String,
    queryParams: List[(String, Option[Any])],
    body: Option[Json]
  )(implicit ec: ExecutionContext): Future[O]
}
