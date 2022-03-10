package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteResponseHeadersModule {
  private case class EdgeRouteResponseHeadersModuleReplace(
    module: Option[EndpointResponseHeaders]
  )

  private object EdgeRouteResponseHeadersModuleReplace {
    implicit val encodeEdgeRouteResponseHeadersModuleReplace: Encoder[EdgeRouteResponseHeadersModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteResponseHeadersModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-response-headers-module">https://ngrok.com/docs/api#api-edge-route-response-headers-module</a>.
  */
class EdgeRouteResponseHeadersModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteResponseHeadersModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-response-headers-module-replace">https://ngrok.com/docs/api#api-edge-route-response-headers-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointResponseHeaders]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointResponseHeaders] = None
  ): Future[EndpointResponseHeaders] =
    apiClient.sendRequest[EndpointResponseHeaders](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/response_headers",
      List.empty,
      Option(
        EdgeRouteResponseHeadersModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-response-headers-module-get">https://ngrok.com/docs/api#api-edge-route-response-headers-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointResponseHeaders] =
    apiClient.sendRequest[EndpointResponseHeaders](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/response_headers",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-response-headers-module-delete">https://ngrok.com/docs/api#api-edge-route-response-headers-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/response_headers",
      List.empty,
      Option.empty
    )

}
