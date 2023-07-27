/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomainCertPolicy]] resource.
  *
  * @constructor create a new ReservedDomainCertPolicy.
  * @param authority certificate authority to request certificates from. The only supported value is letsencrypt.
  * @param privateKeyType type of private key to use when requesting certificates. Defaults to rsa, can be either rsa or ecdsa.
  */
final case class ReservedDomainCertPolicy(
  authority: String,
  privateKeyType: String
)

object ReservedDomainCertPolicy {
  implicit val encodeReservedDomainCertPolicy: io.circe.Encoder[ReservedDomainCertPolicy] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("authority", value.authority.asJson)),
        Option(("private_key_type", value.privateKeyType.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomainCertPolicy: io.circe.Decoder[ReservedDomainCertPolicy] = (c: io.circe.HCursor) =>
    for {
      authority      <- c.downField("authority").as[String]
      privateKeyType <- c.downField("private_key_type").as[String]
    } yield ReservedDomainCertPolicy(
      authority,
      privateKeyType
    )
}
