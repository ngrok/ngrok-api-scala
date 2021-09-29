package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointTlsTerminationModule {
  private case class EndpointTlsTerminationModuleReplace(
    module: Option[EndpointTlsTermination]
  )

  private object EndpointTlsTerminationModuleReplace {
    implicit val encodeEndpointTlsTerminationModuleReplace: Encoder[EndpointTlsTerminationModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointTlsTerminationModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-tls-termination-module">https://ngrok.com/docs/api#api-endpoint-tls-termination-module</a>.
  */
class EndpointTlsTerminationModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointTlsTerminationModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-tls-termination-module-replace">https://ngrok.com/docs/api#api-endpoint-tls-termination-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointTlsTermination]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointTlsTermination] = None
  ): Future[EndpointTlsTermination] =
    apiClient.sendRequest[EndpointTlsTermination](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/tls_termination",
      List.empty,
      Option(
        EndpointTlsTerminationModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-tls-termination-module-get">https://ngrok.com/docs/api#api-endpoint-tls-termination-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointTlsTermination] =
    apiClient.sendRequest[EndpointTlsTermination](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/tls_termination",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-tls-termination-module-delete">https://ngrok.com/docs/api#api-endpoint-tls-termination-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/tls_termination",
      List.empty,
      Option.empty
    )

}
