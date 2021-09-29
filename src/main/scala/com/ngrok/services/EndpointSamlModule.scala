package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointSamlModule {
  private case class EndpointSamlModuleReplace(
    module: Option[EndpointSamlMutate]
  )

  private object EndpointSamlModuleReplace {
    implicit val encodeEndpointSamlModuleReplace: Encoder[EndpointSamlModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointSamlModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-saml-module">https://ngrok.com/docs/api#api-endpoint-saml-module</a>.
  */
class EndpointSamlModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointSamlModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-saml-module-replace">https://ngrok.com/docs/api#api-endpoint-saml-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointSamlMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointSamlMutate] = None
  ): Future[EndpointSaml] =
    apiClient.sendRequest[EndpointSaml](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/saml",
      List.empty,
      Option(
        EndpointSamlModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-saml-module-get">https://ngrok.com/docs/api#api-endpoint-saml-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointSaml] =
    apiClient.sendRequest[EndpointSaml](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/saml",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-saml-module-delete">https://ngrok.com/docs/api#api-endpoint-saml-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/saml",
      List.empty,
      Option.empty
    )

}
