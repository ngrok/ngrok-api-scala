package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointCompressionModule {
  private case class EndpointCompressionModuleReplace(
    module: Option[EndpointCompression]
  )

  private object EndpointCompressionModuleReplace {
    implicit val encodeEndpointCompressionModuleReplace: Encoder[EndpointCompressionModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointCompressionModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-compression-module">https://ngrok.com/docs/api#api-endpoint-compression-module</a>.
  */
class EndpointCompressionModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointCompressionModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-compression-module-replace">https://ngrok.com/docs/api#api-endpoint-compression-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointCompression]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointCompression] = None
  ): Future[EndpointCompression] =
    apiClient.sendRequest[EndpointCompression](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/compression",
      List.empty,
      Option(
        EndpointCompressionModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-compression-module-get">https://ngrok.com/docs/api#api-endpoint-compression-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointCompression] =
    apiClient.sendRequest[EndpointCompression](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/compression",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-compression-module-delete">https://ngrok.com/docs/api#api-endpoint-compression-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/compression",
      List.empty,
      Option.empty
    )

}
