/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshCertificateAuthority]] resource.
  *
  * @constructor create a new SshCertificateAuthority.
  * @param id unique identifier for this SSH Certificate Authority
  * @param uri URI of the SSH Certificate Authority API resource
  * @param createdAt timestamp when the SSH Certificate Authority API resource was created, RFC 3339 format
  * @param description human-readable description of this SSH Certificate Authority. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this SSH Certificate Authority. optional, max 4096 bytes.
  * @param publicKey raw public key for this SSH Certificate Authority
  * @param keyType the type of private key for this SSH Certificate Authority
  */
final case class SshCertificateAuthority(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  publicKey: String,
  keyType: String
)

object SshCertificateAuthority {
  implicit val encodeSshCertificateAuthority: io.circe.Encoder[SshCertificateAuthority] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("public_key", value.publicKey.asJson)),
        Option(("key_type", value.keyType.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshCertificateAuthority: io.circe.Decoder[SshCertificateAuthority] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      publicKey   <- c.downField("public_key").as[String]
      keyType     <- c.downField("key_type").as[String]
    } yield SshCertificateAuthority(
      id,
      uri,
      createdAt,
      description,
      metadata,
      publicKey,
      keyType
    )
}
