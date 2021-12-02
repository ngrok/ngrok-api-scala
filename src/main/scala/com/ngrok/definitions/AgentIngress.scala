package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AgentIngress]] resource.
  *
  * @constructor create a new AgentIngress.
  * @param id unique Agent Ingress resource identifier
  * @param uri URI to the API resource of this Agent ingress
  * @param description human-readable description of the use of this Agent Ingress. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this Agent Ingress. optional, max 4096 bytes
  * @param domain the domain that you own to be used as the base domain name to generate regional agent ingress domains.
  * @param nsTargets a list of target values to use as the values of NS records for the domain property these values will delegate control over the domain to ngrok
  * @param regionDomains a list of regional agent ingress domains that are subdomains of the value of domain this value may increase over time as ngrok adds more regions
  * @param createdAt timestamp when the Agent Ingress was created, RFC 3339 format
  */
final case class AgentIngress(
  id: String,
  uri: java.net.URI,
  description: String,
  metadata: String,
  domain: String,
  nsTargets: List[String],
  regionDomains: List[String],
  createdAt: String
)

object AgentIngress {
  implicit val encodeAgentIngress: io.circe.Encoder[AgentIngress] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("domain", value.domain.asJson)),
      Option(("ns_targets", value.nsTargets.asJson)),
      Option(("region_domains", value.regionDomains.asJson)),
      Option(("created_at", value.createdAt.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeAgentIngress: io.circe.Decoder[AgentIngress] = (c: io.circe.HCursor) =>
    for {
      id            <- c.downField("id").as[String]
      uri           <- c.downField("uri").as[java.net.URI]
      description   <- c.downField("description").as[String]
      metadata      <- c.downField("metadata").as[String]
      domain        <- c.downField("domain").as[String]
      nsTargets     <- c.downField("ns_targets").as[List[String]]
      regionDomains <- c.downField("region_domains").as[List[String]]
      createdAt     <- c.downField("created_at").as[String]
    } yield AgentIngress(
      id,
      uri,
      description,
      metadata,
      domain,
      nsTargets,
      regionDomains,
      createdAt
    )
}
