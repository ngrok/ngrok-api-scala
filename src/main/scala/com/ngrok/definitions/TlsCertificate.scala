/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TlsCertificate]] resource.
  *
  * @constructor create a new TlsCertificate.
  * @param id unique identifier for this TLS certificate
  * @param uri URI of the TLS certificate API resource
  * @param createdAt timestamp when the TLS certificate was created, RFC 3339 format
  * @param description human-readable description of this TLS certificate. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this TLS certificate. optional, max 4096 bytes.
  * @param certificatePem chain of PEM-encoded certificates, leaf first. See <a href="/cloud-edge/endpoints#certificate-chains">Certificate Bundles</a>.
  * @param subjectCommonName subject common name from the leaf of this TLS certificate
  * @param subjectAlternativeNames subject alternative names (SANs) from the leaf of this TLS certificate
  * @param issuedAt timestamp (in RFC 3339 format) when this TLS certificate was issued automatically, or null if this certificate was user-uploaded
  * @param notBefore timestamp when this TLS certificate becomes valid, RFC 3339 format
  * @param notAfter timestamp when this TLS certificate becomes invalid, RFC 3339 format
  * @param keyUsages set of actions the private key of this TLS certificate can be used for
  * @param extendedKeyUsages extended set of actions the private key of this TLS certificate can be used for
  * @param privateKeyType type of the private key of this TLS certificate. One of rsa, ecdsa, or ed25519.
  * @param issuerCommonName issuer common name from the leaf of this TLS certificate
  * @param serialNumber serial number of the leaf of this TLS certificate
  * @param subjectOrganization subject organization from the leaf of this TLS certificate
  * @param subjectOrganizationalUnit subject organizational unit from the leaf of this TLS certificate
  * @param subjectLocality subject locality from the leaf of this TLS certificate
  * @param subjectProvince subject province from the leaf of this TLS certificate
  * @param subjectCountry subject country from the leaf of this TLS certificate
  */
final case class TlsCertificate(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  certificatePem: String,
  subjectCommonName: String,
  subjectAlternativeNames: TlsCertificateSaNs,
  issuedAt: Option[java.time.OffsetDateTime] = None,
  notBefore: java.time.OffsetDateTime,
  notAfter: java.time.OffsetDateTime,
  keyUsages: List[String],
  extendedKeyUsages: List[String],
  privateKeyType: String,
  issuerCommonName: String,
  serialNumber: String,
  subjectOrganization: String,
  subjectOrganizationalUnit: String,
  subjectLocality: String,
  subjectProvince: String,
  subjectCountry: String
)

object TlsCertificate {
  implicit val encodeTlsCertificate: io.circe.Encoder[TlsCertificate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("certificate_pem", value.certificatePem.asJson)),
        Option(("subject_common_name", value.subjectCommonName.asJson)),
        Option(("subject_alternative_names", value.subjectAlternativeNames.asJson)),
        value.issuedAt.map(_.asJson).map(("issued_at", _)),
        Option(("not_before", value.notBefore.asJson)),
        Option(("not_after", value.notAfter.asJson)),
        Option(("key_usages", value.keyUsages.asJson)),
        Option(("extended_key_usages", value.extendedKeyUsages.asJson)),
        Option(("private_key_type", value.privateKeyType.asJson)),
        Option(("issuer_common_name", value.issuerCommonName.asJson)),
        Option(("serial_number", value.serialNumber.asJson)),
        Option(("subject_organization", value.subjectOrganization.asJson)),
        Option(("subject_organizational_unit", value.subjectOrganizationalUnit.asJson)),
        Option(("subject_locality", value.subjectLocality.asJson)),
        Option(("subject_province", value.subjectProvince.asJson)),
        Option(("subject_country", value.subjectCountry.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTlsCertificate: io.circe.Decoder[TlsCertificate] = (c: io.circe.HCursor) =>
    for {
      id                        <- c.downField("id").as[String]
      uri                       <- c.downField("uri").as[java.net.URI]
      createdAt                 <- c.downField("created_at").as[java.time.OffsetDateTime]
      description               <- c.downField("description").as[String]
      metadata                  <- c.downField("metadata").as[String]
      certificatePem            <- c.downField("certificate_pem").as[String]
      subjectCommonName         <- c.downField("subject_common_name").as[String]
      subjectAlternativeNames   <- c.downField("subject_alternative_names").as[TlsCertificateSaNs]
      issuedAt                  <- c.downField("issued_at").as[Option[java.time.OffsetDateTime]]
      notBefore                 <- c.downField("not_before").as[java.time.OffsetDateTime]
      notAfter                  <- c.downField("not_after").as[java.time.OffsetDateTime]
      keyUsages                 <- c.downField("key_usages").as[Option[List[String]]]
      extendedKeyUsages         <- c.downField("extended_key_usages").as[Option[List[String]]]
      privateKeyType            <- c.downField("private_key_type").as[String]
      issuerCommonName          <- c.downField("issuer_common_name").as[String]
      serialNumber              <- c.downField("serial_number").as[String]
      subjectOrganization       <- c.downField("subject_organization").as[String]
      subjectOrganizationalUnit <- c.downField("subject_organizational_unit").as[String]
      subjectLocality           <- c.downField("subject_locality").as[String]
      subjectProvince           <- c.downField("subject_province").as[String]
      subjectCountry            <- c.downField("subject_country").as[String]
    } yield TlsCertificate(
      id,
      uri,
      createdAt,
      description,
      metadata,
      certificatePem,
      subjectCommonName,
      subjectAlternativeNames,
      issuedAt,
      notBefore,
      notAfter,
      keyUsages.getOrElse(List.empty),
      extendedKeyUsages.getOrElse(List.empty),
      privateKeyType,
      issuerCommonName,
      serialNumber,
      subjectOrganization,
      subjectOrganizationalUnit,
      subjectLocality,
      subjectProvince,
      subjectCountry
    )
}
