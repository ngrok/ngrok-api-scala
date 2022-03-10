package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object TlsEdgeBackendModule {
  private case class TlsEdgeBackendModuleReplace(
    module: Option[EndpointBackendMutate]
  )

  private object TlsEdgeBackendModuleReplace {
    implicit val encodeTlsEdgeBackendModuleReplace: Encoder[TlsEdgeBackendModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[TlsEdgeBackendModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-tls-edge-backend-module">https://ngrok.com/docs/api#api-tls-edge-backend-module</a>.
  */
class TlsEdgeBackendModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import TlsEdgeBackendModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-backend-module-replace">https://ngrok.com/docs/api#api-tls-edge-backend-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointBackendMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointBackendMutate] = None
  ): Future[EndpointBackend] =
    apiClient.sendRequest[EndpointBackend](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/tls/$id/backend",
      List.empty,
      Option(
        TlsEdgeBackendModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-backend-module-get">https://ngrok.com/docs/api#api-tls-edge-backend-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointBackend] =
    apiClient.sendRequest[EndpointBackend](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/tls/$id/backend",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-edge-backend-module-delete">https://ngrok.com/docs/api#api-tls-edge-backend-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/tls/$id/backend",
      List.empty,
      Option.empty
    )

}
