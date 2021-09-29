package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointRequestHeadersModule {
  private case class EndpointRequestHeadersModuleReplace(
    module: Option[EndpointRequestHeaders]
  )

  private object EndpointRequestHeadersModuleReplace {
    implicit val encodeEndpointRequestHeadersModuleReplace: Encoder[EndpointRequestHeadersModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointRequestHeadersModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-request-headers-module">https://ngrok.com/docs/api#api-endpoint-request-headers-module</a>.
  */
class EndpointRequestHeadersModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointRequestHeadersModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-request-headers-module-replace">https://ngrok.com/docs/api#api-endpoint-request-headers-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointRequestHeaders]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointRequestHeaders] = None
  ): Future[EndpointRequestHeaders] =
    apiClient.sendRequest[EndpointRequestHeaders](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/request_headers",
      List.empty,
      Option(
        EndpointRequestHeadersModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-request-headers-module-get">https://ngrok.com/docs/api#api-endpoint-request-headers-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointRequestHeaders] =
    apiClient.sendRequest[EndpointRequestHeaders](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/request_headers",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-request-headers-module-delete">https://ngrok.com/docs/api#api-endpoint-request-headers-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/request_headers",
      List.empty,
      Option.empty
    )

}
