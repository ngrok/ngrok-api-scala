/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteOidcModule {
  private case class EdgeRouteOidcModuleReplace(
    module: Option[EndpointOidc]
  )

  private object EdgeRouteOidcModuleReplace {
    implicit val encodeEdgeRouteOidcModuleReplace: Encoder[EdgeRouteOidcModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteOidcModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-oidc-module">https://ngrok.com/docs/api#api-edge-route-oidc-module</a>.
  */
class EdgeRouteOidcModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteOidcModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-oidc-module-replace">https://ngrok.com/docs/api#api-edge-route-oidc-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointOidc]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointOidc] = None
  ): Future[EndpointOidc] =
    apiClient.sendRequest[EndpointOidc](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/oidc",
      List.empty,
      Option(
        EdgeRouteOidcModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-oidc-module-get">https://ngrok.com/docs/api#api-edge-route-oidc-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointOidc] =
    apiClient.sendRequest[EndpointOidc](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/oidc",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-oidc-module-delete">https://ngrok.com/docs/api#api-edge-route-oidc-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/oidc",
      List.empty,
      Option.empty
    )

}
