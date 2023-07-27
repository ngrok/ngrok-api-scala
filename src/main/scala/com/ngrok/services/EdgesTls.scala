/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgesTls {
  private case class EdgesTlsCreate(
    description: Option[String],
    metadata: Option[String],
    hostports: List[String],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTermination]
  )

  private object EdgesTlsCreate {
    implicit val encodeEdgesTlsCreate: Encoder[EdgesTlsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
        value.backend.map(_.asJson).map(("backend", _)),
        value.ipRestriction.map(_.asJson).map(("ip_restriction", _)),
        value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
        value.tlsTermination.map(_.asJson).map(("tls_termination", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class EdgesTlsUpdate(
    description: Option[String],
    metadata: Option[String],
    hostports: List[String],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTermination]
  )

  private object EdgesTlsUpdate {
    implicit val encodeEdgesTlsUpdate: Encoder[EdgesTlsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
        value.backend.map(_.asJson).map(("backend", _)),
        value.ipRestriction.map(_.asJson).map(("ip_restriction", _)),
        value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
        value.tlsTermination.map(_.asJson).map(("tls_termination", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[EdgesTls]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edges-tls">https://ngrok.com/docs/api#api-edges-tls</a>.
  */
class EdgesTls private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgesTls._

  /** Create a TLS Edge
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tls-create">https://ngrok.com/docs/api#api-edges-tls-create</a>.
    *
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param backend edge modules
    * @param ipRestriction the value of the <code>ip_restriction</code> parameter as a [[EndpointIpPolicyMutate]]
    * @param mutualTls the value of the <code>mutual_tls</code> parameter as a [[EndpointMutualTlsMutate]]
    * @param tlsTermination the value of the <code>tls_termination</code> parameter as a [[EndpointTlsTermination]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: List[String] = List.empty,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTermination] = None
  ): Future[TlsEdge] =
    apiClient.sendRequest[TlsEdge](
      NgrokApiClient.HttpMethod.Post,
      "/edges/tls",
      List.empty,
      Option(
        EdgesTlsCreate(
          description,
          metadata,
          hostports,
          backend,
          ipRestriction,
          mutualTls,
          tlsTermination
        ).asJson
      )
    )

  /** Get a TLS Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tls-get">https://ngrok.com/docs/api#api-edges-tls-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[TlsEdge] =
    apiClient.sendRequest[TlsEdge](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/tls/$id",
      List.empty,
      Option.empty
    )

  /** Returns a list of all TLS Edges on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tls-list">https://ngrok.com/docs/api#api-edges-tls-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TlsEdgeList]] =
    apiClient
      .sendRequest[TlsEdgeList](
        NgrokApiClient.HttpMethod.Get,
        "/edges/tls",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Updates a TLS Edge by ID. If a module is not specified in the update, it will
    * not be modified. However, each module configuration that is specified will
    * completely replace the existing value. There is no way to delete an existing
    * module via this API, instead use the delete module API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tls-update">https://ngrok.com/docs/api#api-edges-tls-update</a>.
    *
    * @param id unique identifier of this edge
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param backend edge modules
    * @param ipRestriction the value of the <code>ip_restriction</code> parameter as a [[EndpointIpPolicyMutate]]
    * @param mutualTls the value of the <code>mutual_tls</code> parameter as a [[EndpointMutualTlsMutate]]
    * @param tlsTermination the value of the <code>tls_termination</code> parameter as a [[EndpointTlsTermination]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: List[String] = List.empty,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTermination] = None
  ): Future[TlsEdge] =
    apiClient.sendRequest[TlsEdge](
      NgrokApiClient.HttpMethod.Patch,
      s"/edges/tls/$id",
      List.empty,
      Option(
        EdgesTlsUpdate(
          description,
          metadata,
          hostports,
          backend,
          ipRestriction,
          mutualTls,
          tlsTermination
        ).asJson
      )
    )

  /** Delete a TLS Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-tls-delete">https://ngrok.com/docs/api#api-edges-tls-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/tls/$id",
      List.empty,
      Option.empty
    )

}
