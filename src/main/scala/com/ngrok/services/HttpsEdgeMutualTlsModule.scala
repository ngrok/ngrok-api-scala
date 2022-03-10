package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object HttpsEdgeMutualTlsModule {
  private case class HttpsEdgeMutualTlsModuleReplace(
    module: Option[EndpointMutualTlsMutate]
  )

  private object HttpsEdgeMutualTlsModuleReplace {
    implicit val encodeHttpsEdgeMutualTlsModuleReplace: Encoder[HttpsEdgeMutualTlsModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[HttpsEdgeMutualTlsModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-https-edge-mutual-tls-module">https://ngrok.com/docs/api#api-https-edge-mutual-tls-module</a>.
  */
class HttpsEdgeMutualTlsModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import HttpsEdgeMutualTlsModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-replace">https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointMutualTlsMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointMutualTlsMutate] = None
  ): Future[EndpointMutualTls] =
    apiClient.sendRequest[EndpointMutualTls](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$id/mutual_tls",
      List.empty,
      Option(
        HttpsEdgeMutualTlsModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-get">https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointMutualTls] =
    apiClient.sendRequest[EndpointMutualTls](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$id/mutual_tls",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-delete">https://ngrok.com/docs/api#api-https-edge-mutual-tls-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/https/$id/mutual_tls",
      List.empty,
      Option.empty
    )

}
