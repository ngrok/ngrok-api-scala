package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointWebhookValidationModule {
  private case class EndpointWebhookValidationModuleReplace(
    module: Option[EndpointWebhookValidation]
  )

  private object EndpointWebhookValidationModuleReplace {
    implicit val encodeEndpointWebhookValidationModuleReplace: Encoder[EndpointWebhookValidationModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointWebhookValidationModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-webhook-validation-module">https://ngrok.com/docs/api#api-endpoint-webhook-validation-module</a>.
  */
class EndpointWebhookValidationModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointWebhookValidationModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-replace">https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointWebhookValidation]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointWebhookValidation] = None
  ): Future[EndpointWebhookValidation] =
    apiClient.sendRequest[EndpointWebhookValidation](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/webhook_validation",
      List.empty,
      Option(
        EndpointWebhookValidationModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-get">https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointWebhookValidation] =
    apiClient.sendRequest[EndpointWebhookValidation](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/webhook_validation",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-delete">https://ngrok.com/docs/api#api-endpoint-webhook-validation-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/webhook_validation",
      List.empty,
      Option.empty
    )

}
