/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventDestination]] resource.
  *
  * @constructor create a new EventDestination.
  * @param id Unique identifier for this Event Destination.
  * @param metadata Arbitrary user-defined machine-readable data of this Event Destination. Optional, max 4096 bytes.
  * @param createdAt Timestamp when the Event Destination was created, RFC 3339 format.
  * @param description Human-readable description of the Event Destination. Optional, max 255 bytes.
  * @param format The output format you would like to serialize events into when sending to their target. Currently the only accepted value is <code>JSON</code>.
  * @param target An object that encapsulates where and how to send your events. An event destination must contain exactly one of the following objects, leaving the rest null: <code>kinesis</code>, <code>firehose</code>, <code>cloudwatch_logs</code>, or <code>s3</code>.
  * @param uri URI of the Event Destination API resource.
  */
final case class EventDestination(
  id: String,
  metadata: String,
  createdAt: java.time.OffsetDateTime,
  description: String,
  format: String,
  target: EventTarget,
  uri: java.net.URI
)

object EventDestination {
  implicit val encodeEventDestination: io.circe.Encoder[EventDestination] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("format", value.format.asJson)),
        Option(("target", value.target.asJson)),
        Option(("uri", value.uri.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventDestination: io.circe.Decoder[EventDestination] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      metadata    <- c.downField("metadata").as[String]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      format      <- c.downField("format").as[String]
      target      <- c.downField("target").as[EventTarget]
      uri         <- c.downField("uri").as[java.net.URI]
    } yield EventDestination(
      id,
      metadata,
      createdAt,
      description,
      format,
      target,
      uri
    )
}
