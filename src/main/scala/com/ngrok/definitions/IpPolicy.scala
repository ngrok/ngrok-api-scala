package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpPolicy]] resource.
  *
  * @constructor create a new IpPolicy.
  * @param id unique identifier for this IP policy
  * @param uri URI of the IP Policy API resource
  * @param createdAt timestamp when the IP policy was created, RFC 3339 format
  * @param description human-readable description of the source IPs of this IP policy. optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this IP policy. optional, max 4096 bytes.
  * @param action the IP policy action. Supported values are <code>allow</code> or <code>deny</code>
  */
final case class IpPolicy(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  action: String
)

object IpPolicy {
  implicit val encodeIpPolicy: io.circe.Encoder[IpPolicy] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("action", value.action.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeIpPolicy: io.circe.Decoder[IpPolicy] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      action      <- c.downField("action").as[String]
    } yield IpPolicy(
      id,
      uri,
      createdAt,
      description,
      metadata,
      action
    )
}
