package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshHostCertificate]] resource.
  *
  * @constructor create a new SshHostCertificate.
  * @param id unique identifier for this SSH Host Certificate
  * @param uri URI of the SSH Host Certificate API resource
  * @param createdAt timestamp when the SSH Host Certificate API resource was created, RFC 3339 format
  * @param description human-readable description of this SSH Host Certificate. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this SSH Host Certificate. optional, max 4096 bytes.
  * @param publicKey a public key in OpenSSH Authorized Keys format that this certificate signs
  * @param keyType the key type of the <code>public_key</code>, one of <code>rsa</code>, <code>ecdsa</code> or <code>ed25519</code>
  * @param sshCertificateAuthorityId the ssh certificate authority that is used to sign this ssh host certificate
  * @param principals the list of principals included in the ssh host certificate. This is the list of hostnames and/or IP addresses that are authorized to serve SSH traffic with this certificate. Dangerously, if no principals are specified, this certificate is considered valid for all hosts.
  * @param validAfter the time when the ssh host certificate becomes valid, in RFC 3339 format.
  * @param validUntil the time after which the ssh host certificate becomes invalid, in RFC 3339 format. the OpenSSH certificates RFC calls this <code>valid_before</code>.
  * @param certificate the signed SSH certificate in OpenSSH Authorized Keys format. this value should be placed in a <code>-cert.pub</code> certificate file on disk that should be referenced in your <code>sshd_config</code> configuration file with a <code>HostCertificate</code> directive
  */
final case class SshHostCertificate(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  publicKey: String,
  keyType: String,
  sshCertificateAuthorityId: String,
  principals: List[String],
  validAfter: java.time.OffsetDateTime,
  validUntil: java.time.OffsetDateTime,
  certificate: String
)

object SshHostCertificate {
  implicit val encodeSshHostCertificate: io.circe.Encoder[SshHostCertificate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("public_key", value.publicKey.asJson)),
        Option(("key_type", value.keyType.asJson)),
        Option(("ssh_certificate_authority_id", value.sshCertificateAuthorityId.asJson)),
        Option(("principals", value.principals.asJson)),
        Option(("valid_after", value.validAfter.asJson)),
        Option(("valid_until", value.validUntil.asJson)),
        Option(("certificate", value.certificate.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshHostCertificate: io.circe.Decoder[SshHostCertificate] = (c: io.circe.HCursor) =>
    for {
      id                        <- c.downField("id").as[String]
      uri                       <- c.downField("uri").as[java.net.URI]
      createdAt                 <- c.downField("created_at").as[java.time.OffsetDateTime]
      description               <- c.downField("description").as[String]
      metadata                  <- c.downField("metadata").as[String]
      publicKey                 <- c.downField("public_key").as[String]
      keyType                   <- c.downField("key_type").as[String]
      sshCertificateAuthorityId <- c.downField("ssh_certificate_authority_id").as[String]
      principals                <- c.downField("principals").as[Option[List[String]]]
      validAfter                <- c.downField("valid_after").as[java.time.OffsetDateTime]
      validUntil                <- c.downField("valid_until").as[java.time.OffsetDateTime]
      certificate               <- c.downField("certificate").as[String]
    } yield SshHostCertificate(
      id,
      uri,
      createdAt,
      description,
      metadata,
      publicKey,
      keyType,
      sshCertificateAuthorityId,
      principals.getOrElse(List.empty),
      validAfter,
      validUntil,
      certificate
    )
}
