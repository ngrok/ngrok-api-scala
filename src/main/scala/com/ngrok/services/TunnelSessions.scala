/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object TunnelSessions {}

/** Tunnel Sessions represent instances of ngrok agents or SSH reverse tunnel
  *  sessions that are running and connected to the ngrok service. Each tunnel
  *  session can include one or more Tunnels.
  *
  * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions">https://ngrok.com/docs/api#api-tunnel-sessions</a>.
  */
class TunnelSessions private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import TunnelSessions._

  /** List all online tunnel sessions running on this account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions-list">https://ngrok.com/docs/api#api-tunnel-sessions-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TunnelSessionList]] =
    apiClient
      .sendRequest[TunnelSessionList](
        NgrokApiClient.HttpMethod.Get,
        "/tunnel_sessions",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Get the detailed status of a tunnel session by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions-get">https://ngrok.com/docs/api#api-tunnel-sessions-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[TunnelSession] =
    apiClient.sendRequest[TunnelSession](
      NgrokApiClient.HttpMethod.Get,
      s"/tunnel_sessions/$id",
      List.empty,
      Option.empty
    )

  /** Issues a command instructing the ngrok agent to restart. The agent restarts
    * itself by calling exec() on platforms that support it. This operation is notably
    * not supported on Windows. When an agent restarts, it reconnects with a new
    * tunnel session ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions-restart">https://ngrok.com/docs/api#api-tunnel-sessions-restart</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def restart(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Post,
      s"/tunnel_sessions/$id/restart",
      List.empty,
      Option.empty
    )

  /** Issues a command instructing the ngrok agent that started this tunnel session to
    * exit.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions-stop">https://ngrok.com/docs/api#api-tunnel-sessions-stop</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def stop(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Post,
      s"/tunnel_sessions/$id/stop",
      List.empty,
      Option.empty
    )

  /** Issues a command instructing the ngrok agent to update itself to the latest
    * version. After this call completes successfully, the ngrok agent will be in the
    * update process. A caller should wait some amount of time to allow the update to
    * complete (at least 10 seconds) before making a call to the Restart endpoint to
    * request that the agent restart itself to start using the new code. This call
    * will never update an ngrok agent to a new major version which could cause
    * breaking compatibility issues. If you wish to update to a new major version,
    * that must be done manually. Still, please be aware that updating your ngrok
    * agent could break your integration. This call will fail in any of the following
    * circumstances: there is no update available the ngrok agent&#39;s configuration
    * disabled update checks the agent is currently in process of updating the agent
    * has already successfully updated but has not yet been restarted
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-sessions-update">https://ngrok.com/docs/api#api-tunnel-sessions-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Post,
      s"/tunnel_sessions/$id/update",
      List.empty,
      Option.empty
    )

}
