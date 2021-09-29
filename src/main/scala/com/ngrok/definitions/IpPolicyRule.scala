package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpPolicyRule]] resource.
  *
  * @constructor create a new IpPolicyRule.
  * @param id unique identifier for this IP policy rule
  * @param uri URI of the IP policy rule API resource
  * @param createdAt timestamp when the IP policy rule was created, RFC 3339 format
  * @param description human-readable description of the source IPs of this IP rule. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this IP policy rule. optional, max 4096 bytes.
  * @param cidr an IP or IP range specified in CIDR notation. IPv4 and IPv6 are both supported.
  * @param ipPolicy object describing the IP policy this IP Policy Rule belongs to
  */
final case class IpPolicyRule(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  cidr: String,
  ipPolicy: Ref
)

object IpPolicyRule {
  implicit val encodeIpPolicyRule: io.circe.Encoder[IpPolicyRule] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("cidr", value.cidr.asJson)),
      Option(("ip_policy", value.ipPolicy.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeIpPolicyRule: io.circe.Decoder[IpPolicyRule] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      cidr        <- c.downField("cidr").as[String]
      ipPolicy    <- c.downField("ip_policy").as[Ref]
    } yield IpPolicyRule(
      id,
      uri,
      createdAt,
      description,
      metadata,
      cidr,
      ipPolicy
    )
}
