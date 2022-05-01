package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshUserCertificate]] resource.
  *
  * @constructor create a new SshUserCertificate.
  * @param id unique identifier for this SSH User Certificate
  * @param uri URI of the SSH User Certificate API resource
  * @param createdAt timestamp when the SSH User Certificate API resource was created, RFC 3339 format
  * @param description human-readable description of this SSH User Certificate. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this SSH User Certificate. optional, max 4096 bytes.
  * @param publicKey a public key in OpenSSH Authorized Keys format that this certificate signs
  * @param keyType the key type of the <code>public_key</code>, one of <code>rsa</code>, <code>ecdsa</code> or <code>ed25519</code>
  * @param sshCertificateAuthorityId the ssh certificate authority that is used to sign this ssh user certificate
  * @param principals the list of principals included in the ssh user certificate. This is the list of usernames that the certificate holder may sign in as on a machine authorizing the signing certificate authority. Dangerously, if no principals are specified, this certificate may be used to log in as any user.
  * @param criticalOptions A map of critical options included in the certificate. Only two critical options are currently defined by OpenSSH: <code>force-command</code> and <code>source-address</code>. See <a href="https://github.com/openssh/openssh-portable/blob/master/PROTOCOL.certkeys">the OpenSSH certificate protocol spec</a> for additional details.
  * @param extensions A map of extensions included in the certificate. Extensions are additional metadata that can be interpreted by the SSH server for any purpose. These can be used to permit or deny the ability to open a terminal, do port forwarding, x11 forwarding, and more. If unspecified, the certificate will include limited permissions with the following extension map: <code>{&#34;permit-pty&#34;: &#34;&#34;, &#34;permit-user-rc&#34;: &#34;&#34;}</code> OpenSSH understands a number of predefined extensions. See <a href="https://github.com/openssh/openssh-portable/blob/master/PROTOCOL.certkeys">the OpenSSH certificate protocol spec</a> for additional details.
  * @param validAfter the time when the ssh host certificate becomes valid, in RFC 3339 format.
  * @param validUntil the time after which the ssh host certificate becomes invalid, in RFC 3339 format. the OpenSSH certificates RFC calls this <code>valid_before</code>.
  * @param certificate the signed SSH certificate in OpenSSH Authorized Keys Format. this value should be placed in a <code>-cert.pub</code> certificate file on disk that should be referenced in your <code>sshd_config</code> configuration file with a <code>HostCertificate</code> directive
  */
final case class SshUserCertificate(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  publicKey: String,
  keyType: String,
  sshCertificateAuthorityId: String,
  principals: List[String],
  criticalOptions: Map[String, String],
  extensions: Map[String, String],
  validAfter: java.time.OffsetDateTime,
  validUntil: java.time.OffsetDateTime,
  certificate: String
)

object SshUserCertificate {
  implicit val encodeSshUserCertificate: io.circe.Encoder[SshUserCertificate] =
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
        Option(("critical_options", value.criticalOptions.asJson)),
        Option(("extensions", value.extensions.asJson)),
        Option(("valid_after", value.validAfter.asJson)),
        Option(("valid_until", value.validUntil.asJson)),
        Option(("certificate", value.certificate.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshUserCertificate: io.circe.Decoder[SshUserCertificate] = (c: io.circe.HCursor) =>
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
      criticalOptions           <- c.downField("critical_options").as[Option[Map[String, String]]]
      extensions                <- c.downField("extensions").as[Option[Map[String, String]]]
      validAfter                <- c.downField("valid_after").as[java.time.OffsetDateTime]
      validUntil                <- c.downField("valid_until").as[java.time.OffsetDateTime]
      certificate               <- c.downField("certificate").as[String]
    } yield SshUserCertificate(
      id,
      uri,
      createdAt,
      description,
      metadata,
      publicKey,
      keyType,
      sshCertificateAuthorityId,
      principals.getOrElse(List.empty),
      criticalOptions.getOrElse(Map.empty),
      extensions.getOrElse(Map.empty),
      validAfter,
      validUntil,
      certificate
    )
}
