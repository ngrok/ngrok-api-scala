package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[EndpointOAuthLinkedIn]] resource.
 *
 * @constructor create a new EndpointOAuthLinkedIn.
 * @param clientId the value of the <code>client_id</code> parameter as a [[scala.Predef.String]]
 * @param clientSecret the value of the <code>client_secret</code> parameter as a [[scala.Predef.String]]
 * @param scopes the value of the <code>scopes</code> parameter as a [[scala.List]] of [[String]]
 * @param emailAddresses the value of the <code>email_addresses</code> parameter as a [[scala.List]] of [[String]]
 * @param emailDomains the value of the <code>email_domains</code> parameter as a [[scala.List]] of [[String]]
 */
final case class EndpointOAuthLinkedIn(
  clientId: Option[String] = None,
  clientSecret: Option[String] = None,
  scopes: List[String],
  emailAddresses: List[String],
  emailDomains: List[String],
)

object EndpointOAuthLinkedIn {
  implicit val encodeEndpointOAuthLinkedIn: io.circe.Encoder[EndpointOAuthLinkedIn] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.clientId.map(_.asJson).map(("client_id", _)),
      value.clientSecret.map(_.asJson).map(("client_secret", _)),
      Option(("scopes", value.scopes.asJson)),
      Option(("email_addresses", value.emailAddresses.asJson)),
      Option(("email_domains", value.emailDomains.asJson)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEndpointOAuthLinkedIn: io.circe.Decoder[EndpointOAuthLinkedIn] = (c: io.circe.HCursor) => for {
    clientId <- c.downField("client_id").as[Option[String]]
    clientSecret <- c.downField("client_secret").as[Option[String]]
    scopes <- c.downField("scopes").as[Option[List[String]]]
    emailAddresses <- c.downField("email_addresses").as[Option[List[String]]]
    emailDomains <- c.downField("email_domains").as[Option[List[String]]]
  } yield EndpointOAuthLinkedIn(
    clientId,
    clientSecret,
    scopes.getOrElse(List.empty),
    emailAddresses.getOrElse(List.empty),
    emailDomains.getOrElse(List.empty),
  )
}
