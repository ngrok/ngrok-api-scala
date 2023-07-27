/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object Endpoints {}

/** Endpoints provides an API for querying the endpoint objects
  *  which define what tunnel or edge is used to serve a hostport.
  *  Only active endpoints associated with a tunnel or backend are returned.
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoints">https://ngrok.com/docs/api#api-endpoints</a>.
  */
class Endpoints private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import Endpoints._

  /** List all active endpoints on the account
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoints-list">https://ngrok.com/docs/api#api-endpoints-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[EndpointList]] =
    apiClient
      .sendRequest[EndpointList](
        NgrokApiClient.HttpMethod.Get,
        "/endpoints",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Get the status of an endpoint by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoints-get">https://ngrok.com/docs/api#api-endpoints-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[Endpoint] =
    apiClient.sendRequest[Endpoint](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoints/$id",
      List.empty,
      Option.empty
    )

}
