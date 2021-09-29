package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointResponseHeadersModule {
  private case class EndpointResponseHeadersModuleReplace(
    module: Option[EndpointResponseHeaders]
  )

  private object EndpointResponseHeadersModuleReplace {
    implicit val encodeEndpointResponseHeadersModuleReplace: Encoder[EndpointResponseHeadersModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointResponseHeadersModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-response-headers-module">https://ngrok.com/docs/api#api-endpoint-response-headers-module</a>.
  */
class EndpointResponseHeadersModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointResponseHeadersModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-response-headers-module-replace">https://ngrok.com/docs/api#api-endpoint-response-headers-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointResponseHeaders]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointResponseHeaders] = None
  ): Future[EndpointResponseHeaders] =
    apiClient.sendRequest[EndpointResponseHeaders](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/response_headers",
      List.empty,
      Option(
        EndpointResponseHeadersModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-response-headers-module-get">https://ngrok.com/docs/api#api-endpoint-response-headers-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointResponseHeaders] =
    apiClient.sendRequest[EndpointResponseHeaders](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/response_headers",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-response-headers-module-delete">https://ngrok.com/docs/api#api-endpoint-response-headers-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/response_headers",
      List.empty,
      Option.empty
    )

}
