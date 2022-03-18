package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteOAuthModule {
  private case class EdgeRouteOAuthModuleReplace(
    module: Option[EndpointOAuth]
  )

  private object EdgeRouteOAuthModuleReplace {
    implicit val encodeEdgeRouteOAuthModuleReplace: Encoder[EdgeRouteOAuthModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteOAuthModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-o-auth-module">https://ngrok.com/docs/api#api-edge-route-o-auth-module</a>.
  */
class EdgeRouteOAuthModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteOAuthModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-o-auth-module-replace">https://ngrok.com/docs/api#api-edge-route-o-auth-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointOAuth]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointOAuth] = None
  ): Future[EndpointOAuth] =
    apiClient.sendRequest[EndpointOAuth](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/oauth",
      List.empty,
      Option(
        EdgeRouteOAuthModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-o-auth-module-get">https://ngrok.com/docs/api#api-edge-route-o-auth-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointOAuth] =
    apiClient.sendRequest[EndpointOAuth](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/oauth",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-o-auth-module-delete">https://ngrok.com/docs/api#api-edge-route-o-auth-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/oauth",
      List.empty,
      Option.empty
    )

}
