/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteWebhookVerificationModule {
  private case class EdgeRouteWebhookVerificationModuleReplace(
    module: Option[EndpointWebhookValidation]
  )

  private object EdgeRouteWebhookVerificationModuleReplace {
    implicit val encodeEdgeRouteWebhookVerificationModuleReplace: Encoder[EdgeRouteWebhookVerificationModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteWebhookVerificationModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-webhook-verification-module">https://ngrok.com/docs/api#api-edge-route-webhook-verification-module</a>.
  */
class EdgeRouteWebhookVerificationModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteWebhookVerificationModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-replace">https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointWebhookValidation]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointWebhookValidation] = None
  ): Future[EndpointWebhookValidation] =
    apiClient.sendRequest[EndpointWebhookValidation](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/webhook_verification",
      List.empty,
      Option(
        EdgeRouteWebhookVerificationModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-get">https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointWebhookValidation] =
    apiClient.sendRequest[EndpointWebhookValidation](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/webhook_verification",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-delete">https://ngrok.com/docs/api#api-edge-route-webhook-verification-module-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id/webhook_verification",
      List.empty,
      Option.empty
    )

}
