package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object Tunnels {}

/** Tunnels provide endpoints to access services exposed by a running ngrok
  *  agent tunnel session or an SSH reverse tunnel session.
  *
  * See also <a href="https://ngrok.com/docs/api#api-tunnels">https://ngrok.com/docs/api#api-tunnels</a>.
  */
class Tunnels private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import Tunnels._

  /** List all online tunnels currently running on the account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnels-list">https://ngrok.com/docs/api#api-tunnels-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TunnelList]] =
    apiClient
      .sendRequest[TunnelList](
        NgrokApiClient.HttpMethod.Get,
        "/tunnels",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Get the status of a tunnel by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnels-get">https://ngrok.com/docs/api#api-tunnels-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[Tunnel] =
    apiClient.sendRequest[Tunnel](
      NgrokApiClient.HttpMethod.Get,
      s"/tunnels/$id",
      List.empty,
      Option.empty
    )

}
