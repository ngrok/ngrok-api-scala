package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointIpPolicyModule {
  private case class EndpointIpPolicyModuleReplace(
    module: Option[EndpointIpPolicyMutate]
  )

  private object EndpointIpPolicyModuleReplace {
    implicit val encodeEndpointIpPolicyModuleReplace: Encoder[EndpointIpPolicyModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointIpPolicyModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-ip-policy-module">https://ngrok.com/docs/api#api-endpoint-ip-policy-module</a>.
  */
class EndpointIpPolicyModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointIpPolicyModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-ip-policy-module-replace">https://ngrok.com/docs/api#api-endpoint-ip-policy-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointIpPolicyMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointIpPolicyMutate] = None
  ): Future[EndpointIpPolicy] =
    apiClient.sendRequest[EndpointIpPolicy](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/ip_policy",
      List.empty,
      Option(
        EndpointIpPolicyModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-ip-policy-module-get">https://ngrok.com/docs/api#api-endpoint-ip-policy-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointIpPolicy] =
    apiClient.sendRequest[EndpointIpPolicy](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/ip_policy",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-ip-policy-module-delete">https://ngrok.com/docs/api#api-endpoint-ip-policy-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/ip_policy",
      List.empty,
      Option.empty
    )

}
