package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointCircuitBreakerModule {
  private case class EndpointCircuitBreakerModuleReplace(
    module: Option[EndpointCircuitBreaker]
  )

  private object EndpointCircuitBreakerModuleReplace {
    implicit val encodeEndpointCircuitBreakerModuleReplace: Encoder[EndpointCircuitBreakerModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EndpointCircuitBreakerModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module">https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module</a>.
  */
class EndpointCircuitBreakerModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointCircuitBreakerModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-replace">https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-replace</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointCircuitBreaker]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    id: String,
    module: Option[EndpointCircuitBreaker] = None
  ): Future[EndpointCircuitBreaker] =
    apiClient.sendRequest[EndpointCircuitBreaker](
      NgrokApiClient.HttpMethod.Put,
      s"/endpoint_configurations/$id/circuit_breaker",
      List.empty,
      Option(
        EndpointCircuitBreakerModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-get">https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointCircuitBreaker] =
    apiClient.sendRequest[EndpointCircuitBreaker](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id/circuit_breaker",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-delete">https://ngrok.com/docs/api#api-endpoint-circuit-breaker-module-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id/circuit_breaker",
      List.empty,
      Option.empty
    )

}
