package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[Credential]] resource.
  *
  * @constructor create a new Credential.
  * @param id unique tunnel credential resource identifier
  * @param uri URI of the tunnel credential API resource
  * @param createdAt timestamp when the tunnel credential was created, RFC 3339 format
  * @param description human-readable description of who or what will use the credential to authenticate. Optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this credential. Optional, max 4096 bytes.
  * @param token the credential's authtoken that can be used to authenticate an ngrok agent. <strong>This value is only available one time, on the API response from credential creation, otherwise it is null.</strong>
  * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>'*'</code> is equivalent to no acl at all and will explicitly permit all actions.
  */
final case class Credential(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  token: Option[String] = None,
  acl: List[String]
)

object Credential {
  implicit val encodeCredential: io.circe.Encoder[Credential] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      value.token.map(_.asJson).map(("token", _)),
      Option(("acl", value.acl.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeCredential: io.circe.Decoder[Credential] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      token       <- c.downField("token").as[Option[String]]
      acl         <- c.downField("acl").as[List[String]]
    } yield Credential(
      id,
      uri,
      createdAt,
      description,
      metadata,
      token,
      acl
    )
}
