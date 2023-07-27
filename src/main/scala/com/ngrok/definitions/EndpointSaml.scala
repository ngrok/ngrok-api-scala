/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointSaml]] resource.
  *
  * @constructor create a new EndpointSaml.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param optionsPassthrough Do not enforce authentication on HTTP OPTIONS requests. necessary if you are supporting CORS.
  * @param cookiePrefix the prefix of the session cookie that ngrok sets on the http client to cache authentication. default is &#39;ngrok.&#39;
  * @param inactivityTimeout Integer number of seconds of inactivity after which if the user has not accessed the endpoint, their session will time out and they will be forced to reauthenticate.
  * @param maximumDuration Integer number of seconds of the maximum duration of an authenticated session. After this period is exceeded, a user must reauthenticate.
  * @param idpMetadata The full XML IdP EntityDescriptor. Your IdP may provide this to you as a a file to download or as a URL.
  * @param forceAuthn If true, indicates that whenever we redirect a user to the IdP for authentication that the IdP must prompt the user for authentication credentials even if the user already has a valid session with the IdP.
  * @param allowIdpInitiated If true, the IdP may initiate a login directly (e.g. the user does not need to visit the endpoint first and then be redirected). The IdP should set the <code>RelayState</code> parameter to the target URL of the resource they want the user to be redirected to after the SAML login assertion has been processed.
  * @param authorizedGroups If present, only users who are a member of one of the listed groups may access the target endpoint.
  * @param entityId The SP Entity&#39;s unique ID. This always takes the form of a URL. In ngrok&#39;s implementation, this URL is the same as the metadata URL. This will need to be specified to the IdP as configuration.
  * @param assertionConsumerServiceUrl The public URL of the SP&#39;s Assertion Consumer Service. This is where the IdP will redirect to during an authentication flow. This will need to be specified to the IdP as configuration.
  * @param singleLogoutUrl The public URL of the SP&#39;s Single Logout Service. This is where the IdP will redirect to during a single logout flow. This will optionally need to be specified to the IdP as configuration.
  * @param requestSigningCertificatePem PEM-encoded x.509 certificate of the key pair that is used to sign all SAML requests that the ngrok SP makes to the IdP. Many IdPs do not support request signing verification, but we highly recommend specifying this in the IdP&#39;s configuration if it is supported.
  * @param metadataUrl A public URL where the SP&#39;s metadata is hosted. If an IdP supports dynamic configuration, this is the URL it can use to retrieve the SP metadata.
  * @param nameidFormat Defines the name identifier format the SP expects the IdP to use in its assertions to identify subjects. If unspecified, a default value of <code>urn:oasis:names:tc:SAML:2.0:nameid-format:persistent</code> will be used. A subset of the allowed values enumerated by the SAML specification are supported.
  */
final case class EndpointSaml(
  enabled: Option[Boolean] = None,
  optionsPassthrough: Boolean,
  cookiePrefix: String,
  inactivityTimeout: Long,
  maximumDuration: java.time.Duration,
  idpMetadata: String,
  forceAuthn: Boolean,
  allowIdpInitiated: Option[Boolean] = None,
  authorizedGroups: List[String],
  entityId: String,
  assertionConsumerServiceUrl: java.net.URI,
  singleLogoutUrl: java.net.URI,
  requestSigningCertificatePem: String,
  metadataUrl: java.net.URI,
  nameidFormat: String
)

object EndpointSaml {
  implicit val encodeEndpointSaml: io.circe.Encoder[EndpointSaml] = io.circe.Encoder.encodeJsonObject.contramap(value =>
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
      Option(("entity_id", value.entityId.asJson)),
      Option(("assertion_consumer_service_url", value.assertionConsumerServiceUrl.asJson)),
      Option(("single_logout_url", value.singleLogoutUrl.asJson)),
      Option(("request_signing_certificate_pem", value.requestSigningCertificatePem.asJson)),
      Option(("metadata_url", value.metadataUrl.asJson)),
      Option(("nameid_format", value.nameidFormat.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEndpointSaml: io.circe.Decoder[EndpointSaml] = (c: io.circe.HCursor) =>
    for {
      enabled                      <- c.downField("enabled").as[Option[Boolean]]
      optionsPassthrough           <- c.downField("options_passthrough").as[Boolean]
      cookiePrefix                 <- c.downField("cookie_prefix").as[String]
      inactivityTimeout            <- c.downField("inactivity_timeout").as[Long]
      maximumDuration              <- c.downField("maximum_duration").as[java.time.Duration]
      idpMetadata                  <- c.downField("idp_metadata").as[String]
      forceAuthn                   <- c.downField("force_authn").as[Boolean]
      allowIdpInitiated            <- c.downField("allow_idp_initiated").as[Option[Boolean]]
      authorizedGroups             <- c.downField("authorized_groups").as[Option[List[String]]]
      entityId                     <- c.downField("entity_id").as[String]
      assertionConsumerServiceUrl  <- c.downField("assertion_consumer_service_url").as[java.net.URI]
      singleLogoutUrl              <- c.downField("single_logout_url").as[java.net.URI]
      requestSigningCertificatePem <- c.downField("request_signing_certificate_pem").as[String]
      metadataUrl                  <- c.downField("metadata_url").as[java.net.URI]
      nameidFormat                 <- c.downField("nameid_format").as[String]
    } yield EndpointSaml(
      enabled,
      optionsPassthrough,
      cookiePrefix,
      inactivityTimeout,
      maximumDuration,
      idpMetadata,
      forceAuthn,
      allowIdpInitiated,
      authorizedGroups.getOrElse(List.empty),
      entityId,
      assertionConsumerServiceUrl,
      singleLogoutUrl,
      requestSigningCertificatePem,
      metadataUrl,
      nameidFormat
    )
}
