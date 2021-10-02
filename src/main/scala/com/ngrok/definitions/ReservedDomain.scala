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
  * @param httpEndpointConfiguration object referencing the endpoint configuration applied to http traffic on this domain
  * @param httpsEndpointConfiguration object referencing the endpoint configuration applied to https traffic on this domain
  * @param certificate object referencing the TLS certificate used for connections to this domain. This can be either a user-uploaded certificate, the most recently issued automatic one, or null otherwise.
  * @param certificateManagementPolicy configuration for automatic management of TLS certificates for this domain, or null if automatic management is disabled
  * @param certificateManagementStatus status of the automatic certificate management for this domain, or null if automatic management is disabled
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
  httpEndpointConfiguration: Option[Ref] = None,
  httpsEndpointConfiguration: Option[Ref] = None,
  certificate: Option[Ref] = None,
  certificateManagementPolicy: Option[ReservedDomainCertPolicy] = None,
  certificateManagementStatus: Option[ReservedDomainCertStatus] = None
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
        value.httpEndpointConfiguration.map(_.asJson).map(("http_endpoint_configuration", _)),
        value.httpsEndpointConfiguration.map(_.asJson).map(("https_endpoint_configuration", _)),
        value.certificate.map(_.asJson).map(("certificate", _)),
        value.certificateManagementPolicy.map(_.asJson).map(("certificate_management_policy", _)),
        value.certificateManagementStatus.map(_.asJson).map(("certificate_management_status", _))
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
      httpEndpointConfiguration   <- c.downField("http_endpoint_configuration").as[Option[Ref]]
      httpsEndpointConfiguration  <- c.downField("https_endpoint_configuration").as[Option[Ref]]
      certificate                 <- c.downField("certificate").as[Option[Ref]]
      certificateManagementPolicy <- c.downField("certificate_management_policy").as[Option[ReservedDomainCertPolicy]]
      certificateManagementStatus <- c.downField("certificate_management_status").as[Option[ReservedDomainCertStatus]]
    } yield ReservedDomain(
      id,
      uri,
      createdAt,
      description,
      metadata,
      domain,
      region,
      cnameTarget,
      httpEndpointConfiguration,
      httpsEndpointConfiguration,
      certificate,
      certificateManagementPolicy,
      certificateManagementStatus
    )
}
