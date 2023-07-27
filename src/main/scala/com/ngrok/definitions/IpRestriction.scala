/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpRestriction]] resource.
  *
  * @constructor create a new IpRestriction.
  * @param id unique identifier for this IP restriction
  * @param uri URI of the IP restriction API resource
  * @param createdAt timestamp when the IP restriction was created, RFC 3339 format
  * @param description human-readable description of this IP restriction. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this IP restriction. optional, max 4096 bytes.
  * @param enforced true if the IP restriction will be enforced. if false, only warnings will be issued
  * @param `type` the type of IP restriction. this defines what traffic will be restricted with the attached policies. four values are currently supported: <code>dashboard</code>, <code>api</code>, <code>agent</code>, and <code>endpoints</code>
  * @param ipPolicies the set of IP policies that are used to enforce the restriction
  */
final case class IpRestriction(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  enforced: Boolean,
  `type`: String,
  ipPolicies: List[Ref]
)

object IpRestriction {
  implicit val encodeIpRestriction: io.circe.Encoder[IpRestriction] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("enforced", value.enforced.asJson)),
        Option(("type", value.`type`.asJson)),
        Option(("ip_policies", value.ipPolicies.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeIpRestriction: io.circe.Decoder[IpRestriction] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      enforced    <- c.downField("enforced").as[Boolean]
      `type`      <- c.downField("type").as[String]
      ipPolicies  <- c.downField("ip_policies").as[Option[List[Ref]]]
    } yield IpRestriction(
      id,
      uri,
      createdAt,
      description,
      metadata,
      enforced,
      `type`,
      ipPolicies.getOrElse(List.empty)
    )
}
