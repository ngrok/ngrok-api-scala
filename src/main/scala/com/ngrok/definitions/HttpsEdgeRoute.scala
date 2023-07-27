/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[HttpsEdgeRoute]] resource.
  *
  * @constructor create a new HttpsEdgeRoute.
  * @param edgeId unique identifier of this edge
  * @param id unique identifier of this edge route
  * @param createdAt timestamp when the edge configuration was created, RFC 3339 format
  * @param matchType Type of match to use for this route. Valid values are &#34;exact_path&#34; and &#34;path_prefix&#34;.
  * @param `match` Route selector: &#34;/blog&#34; or &#34;example.com&#34; or &#34;example.com/blog&#34;
  * @param uri URI of the edge API resource
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
  */
final case class HttpsEdgeRoute(
  edgeId: String,
  id: String,
  createdAt: java.time.OffsetDateTime,
  matchType: String,
  `match`: String,
  uri: java.net.URI,
  description: String,
  metadata: String,
  backend: Option[EndpointBackend] = None,
  ipRestriction: Option[EndpointIpPolicy] = None,
  circuitBreaker: Option[EndpointCircuitBreaker] = None,
  compression: Option[EndpointCompression] = None,
  requestHeaders: Option[EndpointRequestHeaders] = None,
  responseHeaders: Option[EndpointResponseHeaders] = None,
  webhookVerification: Option[EndpointWebhookValidation] = None,
  oauth: Option[EndpointOAuth] = None,
  saml: Option[EndpointSaml] = None,
  oidc: Option[EndpointOidc] = None,
  websocketTcpConverter: Option[EndpointWebsocketTcpConverter] = None
)

object HttpsEdgeRoute {
  implicit val encodeHttpsEdgeRoute: io.circe.Encoder[HttpsEdgeRoute] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("edge_id", value.edgeId.asJson)),
        Option(("id", value.id.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("match_type", value.matchType.asJson)),
        Option(("match", value.`match`.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
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

  implicit val decodeHttpsEdgeRoute: io.circe.Decoder[HttpsEdgeRoute] = (c: io.circe.HCursor) =>
    for {
      edgeId                <- c.downField("edge_id").as[String]
      id                    <- c.downField("id").as[String]
      createdAt             <- c.downField("created_at").as[java.time.OffsetDateTime]
      matchType             <- c.downField("match_type").as[String]
      `match`               <- c.downField("match").as[String]
      uri                   <- c.downField("uri").as[java.net.URI]
      description           <- c.downField("description").as[String]
      metadata              <- c.downField("metadata").as[String]
      backend               <- c.downField("backend").as[Option[EndpointBackend]]
      ipRestriction         <- c.downField("ip_restriction").as[Option[EndpointIpPolicy]]
      circuitBreaker        <- c.downField("circuit_breaker").as[Option[EndpointCircuitBreaker]]
      compression           <- c.downField("compression").as[Option[EndpointCompression]]
      requestHeaders        <- c.downField("request_headers").as[Option[EndpointRequestHeaders]]
      responseHeaders       <- c.downField("response_headers").as[Option[EndpointResponseHeaders]]
      webhookVerification   <- c.downField("webhook_verification").as[Option[EndpointWebhookValidation]]
      oauth                 <- c.downField("oauth").as[Option[EndpointOAuth]]
      saml                  <- c.downField("saml").as[Option[EndpointSaml]]
      oidc                  <- c.downField("oidc").as[Option[EndpointOidc]]
      websocketTcpConverter <- c.downField("websocket_tcp_converter").as[Option[EndpointWebsocketTcpConverter]]
    } yield HttpsEdgeRoute(
      edgeId,
      id,
      createdAt,
      matchType,
      `match`,
      uri,
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
    )
}
