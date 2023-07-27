/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshCredential]] resource.
  *
  * @constructor create a new SshCredential.
  * @param id unique ssh credential resource identifier
  * @param uri URI of the ssh credential API resource
  * @param createdAt timestamp when the ssh credential was created, RFC 3339 format
  * @param description human-readable description of who or what will use the ssh credential to authenticate. Optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this ssh credential. Optional, max 4096 bytes.
  * @param publicKey the PEM-encoded public key of the SSH keypair that will be used to authenticate
  * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains, addresses, and labels the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules for domains may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. Bind rules for labels may specify a wildcard key and/or value to match multiple labels. For example, you may specify a rule of <code>bind:*=example</code> which will allow <code>x=example</code>, <code>y=example</code>, etc. A rule of <code>&#39;*&#39;</code> is equivalent to no acl at all and will explicitly permit all actions.
  * @param ownerId If supplied at credential creation, ownership will be assigned to the specified User or Bot. Only admins may specify an owner other than themselves. Defaults to the authenticated User or Bot.
  */
final case class SshCredential(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  publicKey: String,
  acl: List[String],
  ownerId: Option[String] = None
)

object SshCredential {
  implicit val encodeSshCredential: io.circe.Encoder[SshCredential] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("public_key", value.publicKey.asJson)),
        Option(("acl", value.acl.asJson)),
        value.ownerId.map(_.asJson).map(("owner_id", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshCredential: io.circe.Decoder[SshCredential] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      publicKey   <- c.downField("public_key").as[String]
      acl         <- c.downField("acl").as[Option[List[String]]]
      ownerId     <- c.downField("owner_id").as[Option[String]]
    } yield SshCredential(
      id,
      uri,
      createdAt,
      description,
      metadata,
      publicKey,
      acl.getOrElse(List.empty),
      ownerId
    )
}
