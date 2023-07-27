/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object TunnelGroupBackends {
  private case class TunnelGroupBackendsCreate(
    description: Option[String],
    metadata: Option[String],
    labels: Map[String, String]
  )

  private object TunnelGroupBackendsCreate {
    implicit val encodeTunnelGroupBackendsCreate: Encoder[TunnelGroupBackendsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          if (value.labels.isEmpty) None else Option(("labels", value.labels.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class TunnelGroupBackendsUpdate(
    description: Option[String],
    metadata: Option[String],
    labels: Map[String, String]
  )

  private object TunnelGroupBackendsUpdate {
    implicit val encodeTunnelGroupBackendsUpdate: Encoder[TunnelGroupBackendsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          if (value.labels.isEmpty) None else Option(("labels", value.labels.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** A Tunnel Group Backend balances traffic among all online tunnels that match
  *  a label selector.
  *
  * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends">https://ngrok.com/docs/api#api-tunnel-group-backends</a>.
  */
class TunnelGroupBackends private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import TunnelGroupBackends._

  /** Create a new TunnelGroup backend
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends-create">https://ngrok.com/docs/api#api-tunnel-group-backends-create</a>.
    *
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param labels labels to watch for tunnels on, e.g. app-&gt;foo, dc-&gt;bar
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    labels: Map[String, String] = Map.empty
  ): Future[TunnelGroupBackend] =
    apiClient.sendRequest[TunnelGroupBackend](
      NgrokApiClient.HttpMethod.Post,
      "/backends/tunnel_group",
      List.empty,
      Option(
        TunnelGroupBackendsCreate(
          description,
          metadata,
          labels
        ).asJson
      )
    )

  /** Delete a TunnelGroup backend by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends-delete">https://ngrok.com/docs/api#api-tunnel-group-backends-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/backends/tunnel_group/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a TunnelGroup backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends-get">https://ngrok.com/docs/api#api-tunnel-group-backends-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[TunnelGroupBackend] =
    apiClient.sendRequest[TunnelGroupBackend](
      NgrokApiClient.HttpMethod.Get,
      s"/backends/tunnel_group/$id",
      List.empty,
      Option.empty
    )

  /** List all TunnelGroup backends on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends-list">https://ngrok.com/docs/api#api-tunnel-group-backends-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TunnelGroupBackendList]] =
    apiClient
      .sendRequest[TunnelGroupBackendList](
        NgrokApiClient.HttpMethod.Get,
        "/backends/tunnel_group",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update TunnelGroup backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-tunnel-group-backends-update">https://ngrok.com/docs/api#api-tunnel-group-backends-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param labels labels to watch for tunnels on, e.g. app-&gt;foo, dc-&gt;bar
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    labels: Map[String, String] = Map.empty
  ): Future[TunnelGroupBackend] =
    apiClient.sendRequest[TunnelGroupBackend](
      NgrokApiClient.HttpMethod.Patch,
      s"/backends/tunnel_group/$id",
      List.empty,
      Option(
        TunnelGroupBackendsUpdate(
          description,
          metadata,
          labels
        ).asJson
      )
    )

}
