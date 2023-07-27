/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgesHttps {
  private case class EdgesHttpsCreate(
    description: Option[String],
    metadata: Option[String],
    hostports: List[String],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTerminationAtEdge]
  )

  private object EdgesHttpsCreate {
    implicit val encodeEdgesHttpsCreate: Encoder[EdgesHttpsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
        value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
        value.tlsTermination.map(_.asJson).map(("tls_termination", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class EdgesHttpsUpdate(
    description: Option[String],
    metadata: Option[String],
    hostports: List[String],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTerminationAtEdge]
  )

  private object EdgesHttpsUpdate {
    implicit val encodeEdgesHttpsUpdate: Encoder[EdgesHttpsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
        value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
        value.tlsTermination.map(_.asJson).map(("tls_termination", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[EdgesHttps]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edges-https">https://ngrok.com/docs/api#api-edges-https</a>.
  */
class EdgesHttps private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgesHttps._

  /** Create an HTTPS Edge
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-create">https://ngrok.com/docs/api#api-edges-https-create</a>.
    *
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge; optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param mutualTls edge modules
    * @param tlsTermination the value of the <code>tls_termination</code> parameter as a [[EndpointTlsTerminationAtEdge]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: List[String] = List.empty,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTerminationAtEdge] = None
  ): Future[HttpsEdge] =
    apiClient.sendRequest[HttpsEdge](
      NgrokApiClient.HttpMethod.Post,
      "/edges/https",
      List.empty,
      Option(
        EdgesHttpsCreate(
          description,
          metadata,
          hostports,
          mutualTls,
          tlsTermination
        ).asJson
      )
    )

  /** Get an HTTPS Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-get">https://ngrok.com/docs/api#api-edges-https-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[HttpsEdge] =
    apiClient.sendRequest[HttpsEdge](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$id",
      List.empty,
      Option.empty
    )

  /** Returns a list of all HTTPS Edges on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-list">https://ngrok.com/docs/api#api-edges-https-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[HttpsEdgeList]] =
    apiClient
      .sendRequest[HttpsEdgeList](
        NgrokApiClient.HttpMethod.Get,
        "/edges/https",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Updates an HTTPS Edge by ID. If a module is not specified in the update, it will
    * not be modified. However, each module configuration that is specified will
    * completely replace the existing value. There is no way to delete an existing
    * module via this API, instead use the delete module API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-update">https://ngrok.com/docs/api#api-edges-https-update</a>.
    *
    * @param id unique identifier of this edge
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge; optional, max 4096 bytes.
    * @param hostports hostports served by this edge
    * @param mutualTls edge modules
    * @param tlsTermination the value of the <code>tls_termination</code> parameter as a [[EndpointTlsTerminationAtEdge]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    hostports: List[String] = List.empty,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTerminationAtEdge] = None
  ): Future[HttpsEdge] =
    apiClient.sendRequest[HttpsEdge](
      NgrokApiClient.HttpMethod.Patch,
      s"/edges/https/$id",
      List.empty,
      Option(
        EdgesHttpsUpdate(
          description,
          metadata,
          hostports,
          mutualTls,
          tlsTermination
        ).asJson
      )
    )

  /** Delete an HTTPS Edge by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-delete">https://ngrok.com/docs/api#api-edges-https-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/https/$id",
      List.empty,
      Option.empty
    )

}
