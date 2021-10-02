package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointConfiguration]] resource.
  *
  * @constructor create a new EndpointConfiguration.
  * @param id unique identifier of this endpoint configuration
  * @param `type` they type of traffic this endpoint configuration can be applied to. one of: <code>http</code>, <code>https</code>, <code>tcp</code>
  * @param description human-readable description of what this endpoint configuration will be do when applied or what traffic it will be applied to. Optional, max 255 bytes
  * @param metadata arbitrary user-defined machine-readable data of this endpoint configuration. Optional, max 4096 bytes.
  * @param createdAt timestamp when the endpoint configuration was created, RFC 3339 format
  * @param uri URI of the endpoint configuration API resource
  * @param circuitBreaker circuit breaker module configuration or <code>null</code>
  * @param compression compression module configuration or <code>null</code>
  * @param requestHeaders request headers module configuration or <code>null</code>
  * @param responseHeaders response headers module configuration or <code>null</code>
  * @param ipPolicy ip policy module configuration or <code>null</code>
  * @param mutualTls mutual TLS module configuration or <code>null</code>
  * @param tlsTermination TLS termination module configuration or <code>null</code>
  * @param webhookValidation webhook validation module configuration or <code>null</code>
  * @param oauth oauth module configuration or <code>null</code>
  * @param logging logging module configuration or <code>null</code>
  * @param saml saml module configuration or <code>null</code>
  * @param oidc oidc module configuration or <code>null</code>
  */
final case class EndpointConfiguration(
  id: String,
  `type`: String,
  description: String,
  metadata: String,
  createdAt: java.time.OffsetDateTime,
  uri: java.net.URI,
  circuitBreaker: Option[EndpointCircuitBreaker] = None,
  compression: Option[EndpointCompression] = None,
  requestHeaders: Option[EndpointRequestHeaders] = None,
  responseHeaders: Option[EndpointResponseHeaders] = None,
  ipPolicy: Option[EndpointIpPolicy] = None,
  mutualTls: Option[EndpointMutualTls] = None,
  tlsTermination: Option[EndpointTlsTermination] = None,
  webhookValidation: Option[EndpointWebhookValidation] = None,
  oauth: Option[EndpointOAuth] = None,
  logging: Option[EndpointLogging] = None,
  saml: Option[EndpointSaml] = None,
  oidc: Option[EndpointOidc] = None
)

object EndpointConfiguration {
  implicit val encodeEndpointConfiguration: io.circe.Encoder[EndpointConfiguration] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("type", value.`type`.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("uri", value.uri.asJson)),
        value.circuitBreaker.map(_.asJson).map(("circuit_breaker", _)),
        value.compression.map(_.asJson).map(("compression", _)),
        value.requestHeaders.map(_.asJson).map(("request_headers", _)),
        value.responseHeaders.map(_.asJson).map(("response_headers", _)),
        value.ipPolicy.map(_.asJson).map(("ip_policy", _)),
        value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
        value.tlsTermination.map(_.asJson).map(("tls_termination", _)),
        value.webhookValidation.map(_.asJson).map(("webhook_validation", _)),
        value.oauth.map(_.asJson).map(("oauth", _)),
        value.logging.map(_.asJson).map(("logging", _)),
        value.saml.map(_.asJson).map(("saml", _)),
        value.oidc.map(_.asJson).map(("oidc", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointConfiguration: io.circe.Decoder[EndpointConfiguration] = (c: io.circe.HCursor) =>
    for {
      id                <- c.downField("id").as[String]
      `type`            <- c.downField("type").as[String]
      description       <- c.downField("description").as[String]
      metadata          <- c.downField("metadata").as[String]
      createdAt         <- c.downField("created_at").as[java.time.OffsetDateTime]
      uri               <- c.downField("uri").as[java.net.URI]
      circuitBreaker    <- c.downField("circuit_breaker").as[Option[EndpointCircuitBreaker]]
      compression       <- c.downField("compression").as[Option[EndpointCompression]]
      requestHeaders    <- c.downField("request_headers").as[Option[EndpointRequestHeaders]]
      responseHeaders   <- c.downField("response_headers").as[Option[EndpointResponseHeaders]]
      ipPolicy          <- c.downField("ip_policy").as[Option[EndpointIpPolicy]]
      mutualTls         <- c.downField("mutual_tls").as[Option[EndpointMutualTls]]
      tlsTermination    <- c.downField("tls_termination").as[Option[EndpointTlsTermination]]
      webhookValidation <- c.downField("webhook_validation").as[Option[EndpointWebhookValidation]]
      oauth             <- c.downField("oauth").as[Option[EndpointOAuth]]
      logging           <- c.downField("logging").as[Option[EndpointLogging]]
      saml              <- c.downField("saml").as[Option[EndpointSaml]]
      oidc              <- c.downField("oidc").as[Option[EndpointOidc]]
    } yield EndpointConfiguration(
      id,
      `type`,
      description,
      metadata,
      createdAt,
      uri,
      circuitBreaker,
      compression,
      requestHeaders,
      responseHeaders,
      ipPolicy,
      mutualTls,
      tlsTermination,
      webhookValidation,
      oauth,
      logging,
      saml,
      oidc
    )
}
