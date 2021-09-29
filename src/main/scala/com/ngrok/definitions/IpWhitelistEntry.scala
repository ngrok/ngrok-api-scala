package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpWhitelistEntry]] resource.
  *
  * @constructor create a new IpWhitelistEntry.
  * @param id unique identifier for this IP whitelist entry
  * @param uri URI of the IP whitelist entry API resource
  * @param createdAt timestamp when the IP whitelist entry was created, RFC 3339 format
  * @param description human-readable description of the source IPs for this IP whitelist entry. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this IP whitelist entry. optional, max 4096 bytes.
  * @param ipNet an IP address or IP network range in CIDR notation (e.g. 10.1.1.1 or 10.1.0.0/16) of addresses that will be whitelisted to communicate with your tunnel endpoints
  */
final case class IpWhitelistEntry(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  ipNet: String
)

object IpWhitelistEntry {
  implicit val encodeIpWhitelistEntry: io.circe.Encoder[IpWhitelistEntry] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("ip_net", value.ipNet.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeIpWhitelistEntry: io.circe.Decoder[IpWhitelistEntry] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      ipNet       <- c.downField("ip_net").as[String]
    } yield IpWhitelistEntry(
      id,
      uri,
      createdAt,
      description,
      metadata,
      ipNet
    )
}
