package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgesTcp {
  private case class EdgesTcpCreate(
    description: Option[String],
    metadata: Option[String],
    hostports: Option[List[String]],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate]
  )

  private object EdgesTcpCreate {
    implicit val encodeEdgesTcpCreate: Encoder[EdgesTcpCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.hostports.map(_.asJson).map(("hostports", _)),
        value.backend.map(_.asJson).map(("backend", _)),
        value.ipRestriction.map(_.asJson).map(("ip_restriction", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class EdgesTcpUpdate(
    description: Option[String],
    metadata: Option[String],
    hostports: Option[List[String]],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate]
  )

  private object EdgesTcpUpdate {
    implicit val encodeEdgesTcpUpdate: Encoder[EdgesTcpUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.hostports.map(_.asJson).map(("hostports", _)),
        value.backend.map(_.asJson).map(("backend", _)),
        value.ipRestriction.map(_.asJson).map(("ip_restriction", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[EdgesTcp]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edges-tcp">https://ngrok.com/docs/api#api-edges-tcp</a>.
  */
class EdgesTcp private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgesTcp._

  /** Create a TCP Edge
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tcp-create">https://ngrok.com/docs/api#api-edges-tcp-create</a>.
    *
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param backend edge modules
    * @param ipRestriction the value of the <code>ip_restriction</code> parameter as a [[EndpointIpPolicyMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: Option[List[String]] = None,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None
  ): Future[TcpEdge] =
    apiClient.sendRequest[TcpEdge](
      NgrokApiClient.HttpMethod.Post,
      "/edges/tcp",
      List.empty,
      Option(
        EdgesTcpCreate(
          description,
          metadata,
          hostports,
          backend,
          ipRestriction
        ).asJson
      )
    )

  /** Get a TCP Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tcp-get">https://ngrok.com/docs/api#api-edges-tcp-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[TcpEdge] =
    apiClient.sendRequest[TcpEdge](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/tcp/$id",
      List.empty,
      Option.empty
    )

  /** Returns a list of all TCP Edges on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tcp-list">https://ngrok.com/docs/api#api-edges-tcp-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TcpEdgeList]] =
    apiClient
      .sendRequest[TcpEdgeList](
        NgrokApiClient.HttpMethod.Get,
        "/edges/tcp",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Updates a TCP Edge by ID. If a module is not specified in the update, it will
    * not be modified. However, each module configuration that is specified will
    * completely replace the existing value. There is no way to delete an existing
    * module via this API, instead use the delete module API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tcp-update">https://ngrok.com/docs/api#api-edges-tcp-update</a>.
    *
    * @param id unique identifier of this edge
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param backend edge modules
    * @param ipRestriction the value of the <code>ip_restriction</code> parameter as a [[EndpointIpPolicyMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: Option[List[String]] = None,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None
  ): Future[TcpEdge] =
    apiClient.sendRequest[TcpEdge](
      NgrokApiClient.HttpMethod.Patch,
      s"/edges/tcp/$id",
      List.empty,
      Option(
        EdgesTcpUpdate(
          description,
          metadata,
          hostports,
          backend,
          ipRestriction
        ).asJson
      )
    )

  /** Delete a TCP Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tcp-delete">https://ngrok.com/docs/api#api-edges-tcp-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/tcp/$id",
      List.empty,
      Option.empty
    )

}
