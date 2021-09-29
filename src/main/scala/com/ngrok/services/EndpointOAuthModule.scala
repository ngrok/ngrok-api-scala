package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointOAuthModule {
  private case class EndpointOAuthModuleReplace(
    module: Option[EndpointOAuth]
  )

  private object EndpointOAuthModuleReplace {
    implicit val encodeEndpointOAuthModuleReplace: Encoder[EndpointOAuthModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointOAuthModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-o-auth-module">https://ngrok.com/docs/api#api-endpoint-o-auth-module</a>.
  */
class EndpointOAuthModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointOAuthModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-o-auth-module-replace">https://ngrok.com/docs/api#api-endpoint-o-auth-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointOAuth]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointOAuth] = None
  ): Future[EndpointOAuth] =
    apiClient.sendRequest[EndpointOAuth](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/oauth",
      List.empty,
      Option(
        EndpointOAuthModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-o-auth-module-get">https://ngrok.com/docs/api#api-endpoint-o-auth-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointOAuth] =
    apiClient.sendRequest[EndpointOAuth](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/oauth",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-o-auth-module-delete">https://ngrok.com/docs/api#api-endpoint-o-auth-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/oauth",
      List.empty,
      Option.empty
    )

}
