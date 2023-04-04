package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOAuthGitHub]] resource.
  *
  * @constructor create a new EndpointOAuthGitHub.
  * @param clientId the OAuth app client ID. retrieve it from the identity provider&#39;s dashboard where you created your own OAuth app. optional. if unspecified, ngrok will use its own managed oauth application which has additional restrictions. see the OAuth module docs for more details. if present, client_secret must be present as well.
  * @param clientSecret the OAuth app client secret. retrieve if from the identity provider&#39;s dashboard where you created your own OAuth app. optional, see all of the caveats in the docs for <code>client_id</code>.
  * @param scopes a list of provider-specific OAuth scopes with the permissions your OAuth app would like to ask for. these may not be set if you are using the ngrok-managed oauth app (i.e. you must pass both <code>client_id</code> and <code>client_secret</code> to set scopes)
  * @param emailAddresses a list of email addresses of users authenticated by identity provider who are allowed access to the endpoint
  * @param emailDomains a list of email domains of users authenticated by identity provider who are allowed access to the endpoint
  * @param teams a list of github teams identifiers. users will be allowed access to the endpoint if they are a member of any of these teams. identifiers should be in the &#39;slug&#39; format qualified with the org name, e.g. <code>org-name/team-name</code>
  * @param organizations a list of github org identifiers. users who are members of any of the listed organizations will be allowed access. identifiers should be the organization&#39;s &#39;slug&#39;
  */
final case class EndpointOAuthGitHub(
  clientId: Option[String] = None,
  clientSecret: Option[String] = None,
  scopes: Option[List[String]] = None,
  emailAddresses: Option[List[String]] = None,
  emailDomains: Option[List[String]] = None,
  teams: Option[List[String]] = None,
  organizations: Option[List[String]] = None
)

object EndpointOAuthGitHub {
  implicit val encodeEndpointOAuthGitHub: io.circe.Encoder[EndpointOAuthGitHub] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.clientId.map(_.asJson).map(("client_id", _)),
        value.clientSecret.map(_.asJson).map(("client_secret", _)),
        if (value.scopes.isEmpty) None else Option(("scopes", value.scopes.asJson)),
        if (value.emailAddresses.isEmpty) None else Option(("email_addresses", value.emailAddresses.asJson)),
        if (value.emailDomains.isEmpty) None else Option(("email_domains", value.emailDomains.asJson)),
        if (value.teams.isEmpty) None else Option(("teams", value.teams.asJson)),
        if (value.organizations.isEmpty) None else Option(("organizations", value.organizations.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointOAuthGitHub: io.circe.Decoder[EndpointOAuthGitHub] = (c: io.circe.HCursor) =>
    for {
      clientId       <- c.downField("client_id").as[Option[String]]
      clientSecret   <- c.downField("client_secret").as[Option[String]]
      scopes         <- c.downField("scopes").as[Option[List[String]]]
      emailAddresses <- c.downField("email_addresses").as[Option[List[String]]]
      emailDomains   <- c.downField("email_domains").as[Option[List[String]]]
      teams          <- c.downField("teams").as[Option[List[String]]]
      organizations  <- c.downField("organizations").as[Option[List[String]]]
    } yield EndpointOAuthGitHub(
      clientId,
      clientSecret,
      scopes,
      emailAddresses,
      emailDomains,
      teams,
      organizations
    )
}
