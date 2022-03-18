package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteRequestHeadersModule {
  private case class EdgeRouteRequestHeadersModuleReplace(
    module: Option[EndpointRequestHeaders]
  )

  private object EdgeRouteRequestHeadersModuleReplace {
    implicit val encodeEdgeRouteRequestHeadersModuleReplace: Encoder[EdgeRouteRequestHeadersModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteRequestHeadersModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-request-headers-module">https://ngrok.com/docs/api#api-edge-route-request-headers-module</a>.
  */
class EdgeRouteRequestHeadersModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteRequestHeadersModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-request-headers-module-replace">https://ngrok.com/docs/api#api-edge-route-request-headers-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointRequestHeaders]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointRequestHeaders] = None
  ): Future[EndpointRequestHeaders] =
    apiClient.sendRequest[EndpointRequestHeaders](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/request_headers",
      List.empty,
      Option(
        EdgeRouteRequestHeadersModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-request-headers-module-get">https://ngrok.com/docs/api#api-edge-route-request-headers-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointRequestHeaders] =
    apiClient.sendRequest[EndpointRequestHeaders](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/request_headers",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-request-headers-module-delete">https://ngrok.com/docs/api#api-edge-route-request-headers-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/request_headers",
      List.empty,
      Option.empty
    )

}
