package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointLoggingModule {
  private case class EndpointLoggingModuleReplace(
    module: Option[EndpointLoggingMutate]
  )

  private object EndpointLoggingModuleReplace {
    implicit val encodeEndpointLoggingModuleReplace: Encoder[EndpointLoggingModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointLoggingModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-logging-module">https://ngrok.com/docs/api#api-endpoint-logging-module</a>.
  */
class EndpointLoggingModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointLoggingModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-logging-module-replace">https://ngrok.com/docs/api#api-endpoint-logging-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointLoggingMutate]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointLoggingMutate] = None
  ): Future[EndpointLogging] =
    apiClient.sendRequest[EndpointLogging](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/logging",
      List.empty,
      Option(
        EndpointLoggingModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-logging-module-get">https://ngrok.com/docs/api#api-endpoint-logging-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointLogging] =
    apiClient.sendRequest[EndpointLogging](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/logging",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-logging-module-delete">https://ngrok.com/docs/api#api-endpoint-logging-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/logging",
      List.empty,
      Option.empty
    )

}
