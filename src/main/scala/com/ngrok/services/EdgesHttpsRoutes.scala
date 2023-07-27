/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EdgesHttpsRoutes {
  private case class EdgesHttpsRoutesCreate(
    matchType: String,
    `match`: String,
    description: Option[String],
    metadata: Option[String],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate],
    circuitBreaker: Option[EndpointCircuitBreaker],
    compression: Option[EndpointCompression],
    requestHeaders: Option[EndpointRequestHeaders],
    responseHeaders: Option[EndpointResponseHeaders],
    webhookVerification: Option[EndpointWebhookValidation],
    oauth: Option[EndpointOAuth],
    saml: Option[EndpointSamlMutate],
    oidc: Option[EndpointOidc],
    websocketTcpConverter: Option[EndpointWebsocketTcpConverter]
  )

  private object EdgesHttpsRoutesCreate {
    implicit val encodeEdgesHttpsRoutesCreate: Encoder[EdgesHttpsRoutesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          Option(("match_type", value.matchType.asJson)),
          Option(("match", value.`match`.asJson)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backend.map(_.asJson).map(("backend", _)),
          value.ipRestriction.map(_.asJson).map(("ip_restriction", _)),
          value.circuitBreaker.map(_.asJson).map(("circuit_breaker", _)),
          value.compression.map(_.asJson).map(("compression", _)),
          value.requestHeaders.map(_.asJson).map(("request_headers", _)),
          value.responseHeaders.map(_.asJson).map(("response_headers", _)),
          value.webhookVerification.map(_.asJson).map(("webhook_verification", _)),
          value.oauth.map(_.asJson).map(("oauth", _)),
          value.saml.map(_.asJson).map(("saml", _)),
          value.oidc.map(_.asJson).map(("oidc", _)),
          value.websocketTcpConverter.map(_.asJson).map(("websocket_tcp_converter", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class EdgesHttpsRoutesUpdate(
    matchType: Option[String],
    `match`: Option[String],
    description: Option[String],
    metadata: Option[String],
    backend: Option[EndpointBackendMutate],
    ipRestriction: Option[EndpointIpPolicyMutate],
    circuitBreaker: Option[EndpointCircuitBreaker],
    compression: Option[EndpointCompression],
    requestHeaders: Option[EndpointRequestHeaders],
    responseHeaders: Option[EndpointResponseHeaders],
    webhookVerification: Option[EndpointWebhookValidation],
    oauth: Option[EndpointOAuth],
    saml: Option[EndpointSamlMutate],
    oidc: Option[EndpointOidc],
    websocketTcpConverter: Option[EndpointWebsocketTcpConverter]
  )

  private object EdgesHttpsRoutesUpdate {
    implicit val encodeEdgesHttpsRoutesUpdate: Encoder[EdgesHttpsRoutesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.matchType.map(_.asJson).map(("match_type", _)),
          value.`match`.map(_.asJson).map(("match", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backend.map(_.asJson).map(("backend", _)),
          value.ipRestriction.map(_.asJson).map(("ip_restriction", _)),
          value.circuitBreaker.map(_.asJson).map(("circuit_breaker", _)),
          value.compression.map(_.asJson).map(("compression", _)),
          value.requestHeaders.map(_.asJson).map(("request_headers", _)),
          value.responseHeaders.map(_.asJson).map(("response_headers", _)),
          value.webhookVerification.map(_.asJson).map(("webhook_verification", _)),
          value.oauth.map(_.asJson).map(("oauth", _)),
          value.saml.map(_.asJson).map(("saml", _)),
          value.oidc.map(_.asJson).map(("oidc", _)),
          value.websocketTcpConverter.map(_.asJson).map(("websocket_tcp_converter", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EdgesHttpsRoutes]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes">https://ngrok.com/docs/api#api-edges-https-routes</a>.
  */
class EdgesHttpsRoutes private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EdgesHttpsRoutes._

  /** Create an HTTPS Edge Route
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes-create">https://ngrok.com/docs/api#api-edges-https-routes-create</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param matchType Type of match to use for this route. Valid values are &#34;exact_path&#34; and &#34;path_prefix&#34;.
    * @param `match` Route selector: &#34;/blog&#34; or &#34;example.com&#34; or &#34;example.com/blog&#34;
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param backend backend module configuration or <code>null</code>
    * @param ipRestriction ip restriction module configuration or <code>null</code>
    * @param circuitBreaker circuit breaker module configuration or <code>null</code>
    * @param compression compression module configuration or <code>null</code>
    * @param requestHeaders request headers module configuration or <code>null</code>
    * @param responseHeaders response headers module configuration or <code>null</code>
    * @param webhookVerification webhook verification module configuration or <code>null</code>
    * @param oauth oauth module configuration or <code>null</code>
    * @param saml saml module configuration or <code>null</code>
    * @param oidc oidc module configuration or <code>null</code>
    * @param websocketTcpConverter websocket to tcp adapter configuration or <code>null</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    edgeId: String,
    matchType: String,
    `match`: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None,
    circuitBreaker: Option[EndpointCircuitBreaker] = None,
    compression: Option[EndpointCompression] = None,
    requestHeaders: Option[EndpointRequestHeaders] = None,
    responseHeaders: Option[EndpointResponseHeaders] = None,
    webhookVerification: Option[EndpointWebhookValidation] = None,
    oauth: Option[EndpointOAuth] = None,
    saml: Option[EndpointSamlMutate] = None,
    oidc: Option[EndpointOidc] = None,
    websocketTcpConverter: Option[EndpointWebsocketTcpConverter] = None
  ): Future[HttpsEdgeRoute] =
    apiClient.sendRequest[HttpsEdgeRoute](
      NgrokApiClient.HttpMethod.Post,
      s"/edges/https/$edgeId/routes",
      List.empty,
      Option(
        EdgesHttpsRoutesCreate(
          matchType,
          `match`,
          description,
          metadata,
          backend,
          ipRestriction,
          circuitBreaker,
          compression,
          requestHeaders,
          responseHeaders,
          webhookVerification,
          oauth,
          saml,
          oidc,
          websocketTcpConverter
        ).asJson
      )
    )

  /** Get an HTTPS Edge Route by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes-get">https://ngrok.com/docs/api#api-edges-https-routes-get</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    edgeId: String,
    id: String
  ): Future[HttpsEdgeRoute] =
    apiClient.sendRequest[HttpsEdgeRoute](
      NgrokApiClient.HttpMethod.Get,
      s"/edges/https/$edgeId/routes/$id",
      List.empty,
      Option.empty
    )

  /** Updates an HTTPS Edge Route by ID. If a module is not specified in the update,
    * it will not be modified. However, each module configuration that is specified
    * will completely replace the existing value. There is no way to delete an
    * existing module via this API, instead use the delete module API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes-update">https://ngrok.com/docs/api#api-edges-https-routes-update</a>.
    *
    * @param edgeId unique identifier of this edge
    * @param id unique identifier of this edge route
    * @param matchType Type of match to use for this route. Valid values are &#34;exact_path&#34; and &#34;path_prefix&#34;.
    * @param `match` Route selector: &#34;/blog&#34; or &#34;example.com&#34; or &#34;example.com/blog&#34;
    * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
    * @param backend backend module configuration or <code>null</code>
    * @param ipRestriction ip restriction module configuration or <code>null</code>
    * @param circuitBreaker circuit breaker module configuration or <code>null</code>
    * @param compression compression module configuration or <code>null</code>
    * @param requestHeaders request headers module configuration or <code>null</code>
    * @param responseHeaders response headers module configuration or <code>null</code>
    * @param webhookVerification webhook verification module configuration or <code>null</code>
    * @param oauth oauth module configuration or <code>null</code>
    * @param saml saml module configuration or <code>null</code>
    * @param oidc oidc module configuration or <code>null</code>
    * @param websocketTcpConverter websocket to tcp adapter configuration or <code>null</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    edgeId: String,
    id: String,
    matchType: Option[String] = None,
    `match`: Option[String] = None,
    description: Option[String] = None,
    metadata: Option[String] = None,
    backend: Option[EndpointBackendMutate] = None,
    ipRestriction: Option[EndpointIpPolicyMutate] = None,
    circuitBreaker: Option[EndpointCircuitBreaker] = None,
    compression: Option[EndpointCompression] = None,
    requestHeaders: Option[EndpointRequestHeaders] = None,
    responseHeaders: Option[EndpointResponseHeaders] = None,
    webhookVerification: Option[EndpointWebhookValidation] = None,
    oauth: Option[EndpointOAuth] = None,
    saml: Option[EndpointSamlMutate] = None,
    oidc: Option[EndpointOidc] = None,
    websocketTcpConverter: Option[EndpointWebsocketTcpConverter] = None
  ): Future[HttpsEdgeRoute] =
    apiClient.sendRequest[HttpsEdgeRoute](
      NgrokApiClient.HttpMethod.Patch,
      s"/edges/https/$edgeId/routes/$id",
      List.empty,
      Option(
        EdgesHttpsRoutesUpdate(
          matchType,
          `match`,
          description,
          metadata,
          backend,
          ipRestriction,
          circuitBreaker,
          compression,
          requestHeaders,
          responseHeaders,
          webhookVerification,
          oauth,
          saml,
          oidc,
          websocketTcpConverter
        ).asJson
      )
    )

  /** Delete an HTTPS Edge Route by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-edges-https-routes-delete">https://ngrok.com/docs/api#api-edges-https-routes-delete</a>.
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
      s"/edges/https/$edgeId/routes/$id",
      List.empty,
      Option.empty
    )

}
