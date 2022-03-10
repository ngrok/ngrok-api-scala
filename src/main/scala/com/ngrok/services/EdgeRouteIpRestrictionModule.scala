package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteIpRestrictionModule {
  private case class EdgeRouteIpRestrictionModuleReplace(
    module: Option[EndpointIpPolicyMutate]
  )

  private object EdgeRouteIpRestrictionModuleReplace {
    implicit val encodeEdgeRouteIpRestrictionModuleReplace: Encoder[EdgeRouteIpRestrictionModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteIpRestrictionModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-ip-restriction-module">https://ngrok.com/docs/api#api-edge-route-ip-restriction-module</a>.
  */
class EdgeRouteIpRestrictionModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteIpRestrictionModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-replace">https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointIpPolicyMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointIpPolicyMutate] = None
  ): Future[EndpointIpPolicy] =
    apiClient.sendRequest[EndpointIpPolicy](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/ip_restriction",
      List.empty,
      Option(
        EdgeRouteIpRestrictionModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-get">https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointIpPolicy] =
    apiClient.sendRequest[EndpointIpPolicy](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/ip_restriction",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-delete">https://ngrok.com/docs/api#api-edge-route-ip-restriction-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/ip_restriction",
      List.empty,
      Option.empty
    )

}
