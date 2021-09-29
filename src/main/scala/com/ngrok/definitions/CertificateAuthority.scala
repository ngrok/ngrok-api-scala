package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[CertificateAuthority]] resource.
  *
  * @constructor create a new CertificateAuthority.
  * @param id unique identifier for this Certificate Authority
  * @param uri URI of the Certificate Authority API resource
  * @param createdAt timestamp when the Certificate Authority was created, RFC 3339 format
  * @param description human-readable description of this Certificate Authority. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this Certificate Authority. optional, max 4096 bytes.
  * @param caPem raw PEM of the Certificate Authority
  * @param subjectCommonName subject common name of the Certificate Authority
  * @param notBefore timestamp when this Certificate Authority becomes valid, RFC 3339 format
  * @param notAfter timestamp when this Certificate Authority becomes invalid, RFC 3339 format
  * @param keyUsages set of actions the private key of this Certificate Authority can be used for
  * @param extendedKeyUsages extended set of actions the private key of this Certificate Authority can be used for
  */
final case class CertificateAuthority(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  caPem: String,
  subjectCommonName: String,
  notBefore: java.time.OffsetDateTime,
  notAfter: java.time.OffsetDateTime,
  keyUsages: List[String],
  extendedKeyUsages: List[String]
)

object CertificateAuthority {
  implicit val encodeCertificateAuthority: io.circe.Encoder[CertificateAuthority] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("ca_pem", value.caPem.asJson)),
        Option(("subject_common_name", value.subjectCommonName.asJson)),
        Option(("not_before", value.notBefore.asJson)),
        Option(("not_after", value.notAfter.asJson)),
        Option(("key_usages", value.keyUsages.asJson)),
        Option(("extended_key_usages", value.extendedKeyUsages.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeCertificateAuthority: io.circe.Decoder[CertificateAuthority] = (c: io.circe.HCursor) =>
    for {
      id                <- c.downField("id").as[String]
      uri               <- c.downField("uri").as[java.net.URI]
      createdAt         <- c.downField("created_at").as[java.time.OffsetDateTime]
      description       <- c.downField("description").as[String]
      metadata          <- c.downField("metadata").as[String]
      caPem             <- c.downField("ca_pem").as[String]
      subjectCommonName <- c.downField("subject_common_name").as[String]
      notBefore         <- c.downField("not_before").as[java.time.OffsetDateTime]
      notAfter          <- c.downField("not_after").as[java.time.OffsetDateTime]
      keyUsages         <- c.downField("key_usages").as[List[String]]
      extendedKeyUsages <- c.downField("extended_key_usages").as[List[String]]
    } yield CertificateAuthority(
      id,
      uri,
      createdAt,
      description,
      metadata,
      caPem,
      subjectCommonName,
      notBefore,
      notAfter,
      keyUsages,
      extendedKeyUsages
    )
}
