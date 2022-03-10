package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteCompressionModule {
  private case class EdgeRouteCompressionModuleReplace(
    module: Option[EndpointCompression]
  )

  private object EdgeRouteCompressionModuleReplace {
    implicit val encodeEdgeRouteCompressionModuleReplace: Encoder[EdgeRouteCompressionModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteCompressionModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-compression-module">https://ngrok.com/docs/api#api-edge-route-compression-module</a>.
  */
class EdgeRouteCompressionModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteCompressionModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-compression-module-replace">https://ngrok.com/docs/api#api-edge-route-compression-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointCompression]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointCompression] = None
  ): Future[EndpointCompression] =
    apiClient.sendRequest[EndpointCompression](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/compression",
      List.empty,
      Option(
        EdgeRouteCompressionModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-compression-module-get">https://ngrok.com/docs/api#api-edge-route-compression-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointCompression] =
    apiClient.sendRequest[EndpointCompression](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/compression",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-compression-module-delete">https://ngrok.com/docs/api#api-edge-route-compression-module-delete</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    edgeId: String,
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/https/$edgeId/routes/$id/compression",
      List.empty,
      Option.empty
    )

}
