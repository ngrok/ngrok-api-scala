/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object TlsEdgeMutualTlsModule {
  private case class TlsEdgeMutualTlsModuleReplace(
    module: Option[EndpointMutualTlsMutate]
  )

  private object TlsEdgeMutualTlsModuleReplace {
    implicit val encodeTlsEdgeMutualTlsModuleReplace: Encoder[TlsEdgeMutualTlsModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[TlsEdgeMutualTlsModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module">https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module</a>.
  */
class TlsEdgeMutualTlsModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import TlsEdgeMutualTlsModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-replace">https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-replace</a>.
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
      s"/edges/tls/$id/mutual_tls",
      List.empty,
      Option(
        TlsEdgeMutualTlsModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-get">https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointMutualTls] =
    apiClient.sendRequest[EndpointMutualTls](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/tls/$id/mutual_tls",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-delete">https://ngrok.com/docs/api#api-tls-edge-mutual-tls-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/tls/$id/mutual_tls",
      List.empty,
      Option.empty
    )

}
