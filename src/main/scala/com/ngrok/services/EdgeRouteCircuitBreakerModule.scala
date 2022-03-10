package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgeRouteCircuitBreakerModule {
  private case class EdgeRouteCircuitBreakerModuleReplace(
    module: Option[EndpointCircuitBreaker]
  )

  private object EdgeRouteCircuitBreakerModuleReplace {
    implicit val encodeEdgeRouteCircuitBreakerModuleReplace: Encoder[EdgeRouteCircuitBreakerModuleReplace] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.module.map(_.asJson).map(("module", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgeRouteCircuitBreakerModule]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module">https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module</a>.
  */
class EdgeRouteCircuitBreakerModule private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgeRouteCircuitBreakerModule._

  /** Sends an API request for the Replace operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-replace">https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-replace</a>.
    *
    * @param edgeId the value of the <code>edge_id</code> parameter as a [[scala.Predef.String]]
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param module the value of the <code>module</code> parameter as a [[EndpointCircuitBreaker]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def replace(
    edgeId: String,
    id: String,
    module: Option[EndpointCircuitBreaker] = None
  ): Future[EndpointCircuitBreaker] =
    apiClient.sendRequest[EndpointCircuitBreaker](
      NgrokApiClient.HttpMethod.Put,
      s"/edges/https/$edgeId/routes/$id/circuit_breaker",
      List.empty,
      Option(
        EdgeRouteCircuitBreakerModuleReplace(
          module
        ).asJson
      )
    )

  /** Sends an API request for the Get operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-get">https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[EndpointCircuitBreaker] =
    apiClient.sendRequest[EndpointCircuitBreaker](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id/circuit_breaker",
      List.empty,
      Option.empty
    )

  /** Sends an API request for the Delete operation.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-delete">https://ngrok.com/docs/api#api-edge-route-circuit-breaker-module-delete</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    edgeId: String,
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/edges/https/$edgeId/routes/$id/circuit_breaker",
      List.empty,
      Option.empty
    )

}
