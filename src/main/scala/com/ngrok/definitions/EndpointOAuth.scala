package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOAuth]] resource.
  *
  * @constructor create a new EndpointOAuth.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param provider an object which defines the identity provider to use for authentication and configuration for who may access the endpoint
  * @param optionsPassthrough Do not enforce authentication on HTTP OPTIONS requests. necessary if you are supporting CORS.
  * @param cookiePrefix the prefix of the session cookie that ngrok sets on the http client to cache authentication. default is 'ngrok.'
  * @param inactivityTimeout Integer number of seconds of inactivity after which if the user has not accessed the endpoint, their session will time out and they will be forced to reauthenticate.
  * @param maximumDuration Integer number of seconds of the maximum duration of an authenticated session. After this period is exceeded, a user must reauthenticate.
  * @param authCheckInterval Integer number of seconds after which ngrok guarantees it will refresh user state from the identity provider and recheck whether the user is still authorized to access the endpoint. This is the preferred tunable to use to enforce a minimum amount of time after which a revoked user will no longer be able to access the resource.
  */
final case class EndpointOAuth(
  enabled: Option[Boolean] = None,
  provider: EndpointOAuthProvider,
  optionsPassthrough: Boolean,
  cookiePrefix: String,
  inactivityTimeout: Long,
  maximumDuration: java.time.Duration,
  authCheckInterval: Long
)

object EndpointOAuth {
  implicit val encodeEndpointOAuth: io.circe.Encoder[EndpointOAuth] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("provider", value.provider.asJson)),
        Option(("options_passthrough", value.optionsPassthrough.asJson)),
        Option(("cookie_prefix", value.cookiePrefix.asJson)),
        Option(("inactivity_timeout", value.inactivityTimeout.asJson)),
        Option(("maximum_duration", value.maximumDuration.asJson)),
        Option(("auth_check_interval", value.authCheckInterval.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointOAuth: io.circe.Decoder[EndpointOAuth] = (c: io.circe.HCursor) =>
    for {
      enabled            <- c.downField("enabled").as[Option[Boolean]]
      provider           <- c.downField("provider").as[EndpointOAuthProvider]
      optionsPassthrough <- c.downField("options_passthrough").as[Boolean]
      cookiePrefix       <- c.downField("cookie_prefix").as[String]
      inactivityTimeout  <- c.downField("inactivity_timeout").as[Long]
      maximumDuration    <- c.downField("maximum_duration").as[java.time.Duration]
      authCheckInterval  <- c.downField("auth_check_interval").as[Long]
    } yield EndpointOAuth(
      enabled,
      provider,
      optionsPassthrough,
      cookiePrefix,
      inactivityTimeout,
      maximumDuration,
      authCheckInterval
    )
}
