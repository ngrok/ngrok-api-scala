package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOAuthMicrosoft]] resource.
  *
  * @constructor create a new EndpointOAuthMicrosoft.
  * @param clientId the OAuth app client ID. retrieve it from the identity provider's dashboard where you created your own OAuth app. optional. if unspecified, ngrok will use its own managed oauth application which has additional restrictions. see the OAuth module docs for more details. if present, client_secret must be present as well.
  * @param clientSecret the OAuth app client secret. retrieve if from the identity provider's dashboard where you created your own OAuth app. optional, see all of the caveats in the docs for <code>client_id</code>.
  * @param scopes a list of provider-specific OAuth scopes with the permissions your OAuth app would like to ask for. these may not be set if you are using the ngrok-managed oauth app (i.e. you must pass both <code>client_id</code> and <code>client_secret</code> to set scopes)
  * @param emailAddresses a list of email addresses of users authenticated by identity provider who are allowed access to the endpoint
  * @param emailDomains a list of email domains of users authenticated by identity provider who are allowed access to the endpoint
  */
final case class EndpointOAuthMicrosoft(
  clientId: Option[String] = None,
  clientSecret: Option[String] = None,
  scopes: List[String],
  emailAddresses: List[String],
  emailDomains: List[String]
)

object EndpointOAuthMicrosoft {
  implicit val encodeEndpointOAuthMicrosoft: io.circe.Encoder[EndpointOAuthMicrosoft] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.clientId.map(_.asJson).map(("client_id", _)),
        value.clientSecret.map(_.asJson).map(("client_secret", _)),
        Option(("scopes", value.scopes.asJson)),
        Option(("email_addresses", value.emailAddresses.asJson)),
        Option(("email_domains", value.emailDomains.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointOAuthMicrosoft: io.circe.Decoder[EndpointOAuthMicrosoft] = (c: io.circe.HCursor) =>
    for {
      clientId       <- c.downField("client_id").as[Option[String]]
      clientSecret   <- c.downField("client_secret").as[Option[String]]
      scopes         <- c.downField("scopes").as[List[String]]
      emailAddresses <- c.downField("email_addresses").as[List[String]]
      emailDomains   <- c.downField("email_domains").as[List[String]]
    } yield EndpointOAuthMicrosoft(
      clientId,
      clientSecret,
      scopes,
      emailAddresses,
      emailDomains
    )
}
