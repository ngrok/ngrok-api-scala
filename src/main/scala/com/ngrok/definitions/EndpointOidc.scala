package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOidc]] resource.
  *
  * @constructor create a new EndpointOidc.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param optionsPassthrough Do not enforce authentication on HTTP OPTIONS requests. necessary if you are supporting CORS.
  * @param cookiePrefix the prefix of the session cookie that ngrok sets on the http client to cache authentication. default is 'ngrok.'
  * @param inactivityTimeout Integer number of seconds of inactivity after which if the user has not accessed the endpoint, their session will time out and they will be forced to reauthenticate.
  * @param maximumDuration Integer number of seconds of the maximum duration of an authenticated session. After this period is exceeded, a user must reauthenticate.
  * @param issuer URL of the OIDC "OpenID provider". This is the base URL used for discovery.
  * @param clientId The OIDC app's client ID and OIDC audience.
  * @param clientSecret The OIDC app's client secret.
  * @param scopes The set of scopes to request from the OIDC identity provider.
  */
final case class EndpointOidc(
  enabled: Option[Boolean],
  optionsPassthrough: Boolean,
  cookiePrefix: String,
  inactivityTimeout: Long,
  maximumDuration: java.time.Duration,
  issuer: String,
  clientId: String,
  clientSecret: String,
  scopes: List[String]
)

object EndpointOidc {
  implicit val encodeEndpointOidc: io.circe.Encoder[EndpointOidc] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.enabled.map(_.asJson).map(("enabled", _)),
      Option(("options_passthrough", value.optionsPassthrough.asJson)),
      Option(("cookie_prefix", value.cookiePrefix.asJson)),
      Option(("inactivity_timeout", value.inactivityTimeout.asJson)),
      Option(("maximum_duration", value.maximumDuration.asJson)),
      Option(("issuer", value.issuer.asJson)),
      Option(("client_id", value.clientId.asJson)),
      Option(("client_secret", value.clientSecret.asJson)),
      Option(("scopes", value.scopes.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEndpointOidc: io.circe.Decoder[EndpointOidc] = (c: io.circe.HCursor) =>
    for {
      enabled            <- c.downField("enabled").as[Option[Boolean]]
      optionsPassthrough <- c.downField("options_passthrough").as[Boolean]
      cookiePrefix       <- c.downField("cookie_prefix").as[String]
      inactivityTimeout  <- c.downField("inactivity_timeout").as[Long]
      maximumDuration    <- c.downField("maximum_duration").as[java.time.Duration]
      issuer             <- c.downField("issuer").as[String]
      clientId           <- c.downField("client_id").as[String]
      clientSecret       <- c.downField("client_secret").as[String]
      scopes             <- c.downField("scopes").as[List[String]]
    } yield EndpointOidc(
      enabled,
      optionsPassthrough,
      cookiePrefix,
      inactivityTimeout,
      maximumDuration,
      issuer,
      clientId,
      clientSecret,
      scopes
    )
}
