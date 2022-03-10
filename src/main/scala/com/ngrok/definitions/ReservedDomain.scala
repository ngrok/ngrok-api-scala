package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomain]] resource.
  *
  * @constructor create a new ReservedDomain.
  * @param id unique reserved domain resource identifier
  * @param uri URI of the reserved domain API resource
  * @param createdAt timestamp when the reserved domain was created, RFC 3339 format
  * @param description human-readable description of what this reserved domain will be used for
  * @param metadata arbitrary user-defined machine-readable data of this reserved domain. Optional, max 4096 bytes.
  * @param domain hostname of the reserved domain
  * @param region reserve the domain in this geographic ngrok datacenter. Optional, default is us. (au, eu, ap, us, jp, in, sa)
  * @param cnameTarget DNS CNAME target for a custom hostname, or null if the reserved domain is a subdomain of *.ngrok.io
  * @param certificate object referencing the TLS certificate used for connections to this domain. This can be either a user-uploaded certificate, the most recently issued automatic one, or null otherwise.
  * @param certificateManagementPolicy configuration for automatic management of TLS certificates for this domain, or null if automatic management is disabled
  * @param certificateManagementStatus status of the automatic certificate management for this domain, or null if automatic management is disabled
  * @param acmeChallengeCnameTarget DNS CNAME target for the host _acme-challenge.example.com, where example.com is your reserved domain name. This is required to issue certificates for wildcard, non-ngrok reserved domains. Must be null for non-wildcard domains and ngrok subdomains.
  */
final case class ReservedDomain(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  domain: String,
  region: String,
  cnameTarget: Option[String] = None,
  certificate: Option[Ref] = None,
  certificateManagementPolicy: Option[ReservedDomainCertPolicy] = None,
  certificateManagementStatus: Option[ReservedDomainCertStatus] = None,
  acmeChallengeCnameTarget: Option[String] = None
)

object ReservedDomain {
  implicit val encodeReservedDomain: io.circe.Encoder[ReservedDomain] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("domain", value.domain.asJson)),
        Option(("region", value.region.asJson)),
        value.cnameTarget.map(_.asJson).map(("cname_target", _)),
        value.certificate.map(_.asJson).map(("certificate", _)),
        value.certificateManagementPolicy.map(_.asJson).map(("certificate_management_policy", _)),
        value.certificateManagementStatus.map(_.asJson).map(("certificate_management_status", _)),
        value.acmeChallengeCnameTarget.map(_.asJson).map(("acme_challenge_cname_target", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomain: io.circe.Decoder[ReservedDomain] = (c: io.circe.HCursor) =>
    for {
      id                          <- c.downField("id").as[String]
      uri                         <- c.downField("uri").as[java.net.URI]
      createdAt                   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description                 <- c.downField("description").as[String]
      metadata                    <- c.downField("metadata").as[String]
      domain                      <- c.downField("domain").as[String]
      region                      <- c.downField("region").as[String]
      cnameTarget                 <- c.downField("cname_target").as[Option[String]]
      certificate                 <- c.downField("certificate").as[Option[Ref]]
      certificateManagementPolicy <- c.downField("certificate_management_policy").as[Option[ReservedDomainCertPolicy]]
      certificateManagementStatus <- c.downField("certificate_management_status").as[Option[ReservedDomainCertStatus]]
      acmeChallengeCnameTarget    <- c.downField("acme_challenge_cname_target").as[Option[String]]
    } yield ReservedDomain(
      id,
      uri,
      createdAt,
      description,
      metadata,
      domain,
      region,
      cnameTarget,
      certificate,
      certificateManagementPolicy,
      certificateManagementStatus,
      acmeChallengeCnameTarget
    )
}
