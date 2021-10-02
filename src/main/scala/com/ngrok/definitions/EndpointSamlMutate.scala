package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointSamlMutate]] resource.
  *
  * @constructor create a new EndpointSamlMutate.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param optionsPassthrough Do not enforce authentication on HTTP OPTIONS requests. necessary if you are supporting CORS.
  * @param cookiePrefix the prefix of the session cookie that ngrok sets on the http client to cache authentication. default is 'ngrok.'
  * @param inactivityTimeout Integer number of seconds of inactivity after which if the user has not accessed the endpoint, their session will time out and they will be forced to reauthenticate.
  * @param maximumDuration Integer number of seconds of the maximum duration of an authenticated session. After this period is exceeded, a user must reauthenticate.
  * @param idpMetadata The full XML IdP EntityDescriptor. Your IdP may provide this to you as a a file to download or as a URL.
  * @param forceAuthn If true, indicates that whenever we redirect a user to the IdP for authentication that the IdP must prompt the user for authentication credentials even if the user already has a valid session with the IdP.
  * @param allowIdpInitiated If true, the IdP may initiate a login directly (e.g. the user does not need to visit the endpoint first and then be redirected). The IdP should set the <code>RelayState</code> parameter to the target URL of the resource they want the user to be redirected to after the SAML login assertion has been processed.
  * @param authorizedGroups If present, only users who are a member of one of the listed groups may access the target endpoint.
  * @param nameidFormat Defines the name identifier format the SP expects the IdP to use in its assertions to identify subjects. If unspecified, a default value of <code>urn:oasis:names:tc:SAML:2.0:nameid-format:persistent</code> will be used. A subset of the allowed values enumerated by the SAML specification are supported.
  */
final case class EndpointSamlMutate(
  enabled: Option[Boolean] = None,
  optionsPassthrough: Boolean,
  cookiePrefix: String,
  inactivityTimeout: Long,
  maximumDuration: java.time.Duration,
  idpMetadata: String,
  forceAuthn: Boolean,
  allowIdpInitiated: Option[Boolean] = None,
  authorizedGroups: List[String],
  nameidFormat: String
)

object EndpointSamlMutate {
  implicit val encodeEndpointSamlMutate: io.circe.Encoder[EndpointSamlMutate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("options_passthrough", value.optionsPassthrough.asJson)),
        Option(("cookie_prefix", value.cookiePrefix.asJson)),
        Option(("inactivity_timeout", value.inactivityTimeout.asJson)),
        Option(("maximum_duration", value.maximumDuration.asJson)),
        Option(("idp_metadata", value.idpMetadata.asJson)),
        Option(("force_authn", value.forceAuthn.asJson)),
        value.allowIdpInitiated.map(_.asJson).map(("allow_idp_initiated", _)),
        Option(("authorized_groups", value.authorizedGroups.asJson)),
        Option(("nameid_format", value.nameidFormat.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointSamlMutate: io.circe.Decoder[EndpointSamlMutate] = (c: io.circe.HCursor) =>
    for {
      enabled            <- c.downField("enabled").as[Option[Boolean]]
      optionsPassthrough <- c.downField("options_passthrough").as[Boolean]
      cookiePrefix       <- c.downField("cookie_prefix").as[String]
      inactivityTimeout  <- c.downField("inactivity_timeout").as[Long]
      maximumDuration    <- c.downField("maximum_duration").as[java.time.Duration]
      idpMetadata        <- c.downField("idp_metadata").as[String]
      forceAuthn         <- c.downField("force_authn").as[Boolean]
      allowIdpInitiated  <- c.downField("allow_idp_initiated").as[Option[Boolean]]
      authorizedGroups   <- c.downField("authorized_groups").as[List[String]]
      nameidFormat       <- c.downField("nameid_format").as[String]
    } yield EndpointSamlMutate(
      enabled,
      optionsPassthrough,
      cookiePrefix,
      inactivityTimeout,
      maximumDuration,
      idpMetadata,
      forceAuthn,
      allowIdpInitiated,
      authorizedGroups,
      nameidFormat
    )
}
