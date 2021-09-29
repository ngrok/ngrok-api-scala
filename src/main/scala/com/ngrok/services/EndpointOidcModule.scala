package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointOidcModule {
  private case class EndpointOidcModuleReplace(
    module: Option[EndpointOidc]
  )

  private object EndpointOidcModuleReplace {
    implicit val encodeEndpointOidcModuleReplace: Encoder[EndpointOidcModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointOidcModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-oidc-module">https://ngrok.com/docs/api#api-endpoint-oidc-module</a>.
  */
class EndpointOidcModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointOidcModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-oidc-module-replace">https://ngrok.com/docs/api#api-endpoint-oidc-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointOidc]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointOidc] = None
  ): Future[EndpointOidc] =
    apiClient.sendRequest[EndpointOidc](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/oidc",
      List.empty,
      Option(
        EndpointOidcModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-oidc-module-get">https://ngrok.com/docs/api#api-endpoint-oidc-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointOidc] =
    apiClient.sendRequest[EndpointOidc](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/oidc",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-oidc-module-delete">https://ngrok.com/docs/api#api-endpoint-oidc-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/oidc",
      List.empty,
      Option.empty
    )

}
