package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventStream]] resource.
  *
  * @constructor create a new EventStream.
  * @param id Unique identifier for this Event Stream.
  * @param uri URI of the Event Stream API resource.
  * @param createdAt Timestamp when the Event Stream was created, RFC 3339 format.
  * @param metadata Arbitrary user-defined machine-readable data of this Event Stream. Optional, max 4096 bytes.
  * @param description Human-readable description of the Event Stream. Optional, max 255 bytes.
  * @param fields A list of protocol-specific fields you want to collect on each event.
  * @param eventType The protocol that determines which events will be collected. Supported values are <code>tcp_connection_closed</code> and <code>http_request_complete</code>.
  * @param destinationIds A list of Event Destination IDs which should be used for this Event Stream. Event Streams are required to have at least one Event Destination.
  * @param samplingRate The percentage of all events you would like to capture. Valid values range from 0.01, representing 1% of all events to 1.00, representing 100% of all events.
  */
final case class EventStream(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  metadata: String,
  description: String,
  fields: List[String],
  eventType: String,
  destinationIds: List[String],
  samplingRate: Double
)

object EventStream {
  implicit val encodeEventStream: io.circe.Encoder[EventStream] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("description", value.description.asJson)),
      Option(("fields", value.fields.asJson)),
      Option(("event_type", value.eventType.asJson)),
      Option(("destination_ids", value.destinationIds.asJson)),
      Option(("sampling_rate", value.samplingRate.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEventStream: io.circe.Decoder[EventStream] = (c: io.circe.HCursor) =>
    for {
      id             <- c.downField("id").as[String]
      uri            <- c.downField("uri").as[java.net.URI]
      createdAt      <- c.downField("created_at").as[java.time.OffsetDateTime]
      metadata       <- c.downField("metadata").as[String]
      description    <- c.downField("description").as[String]
      fields         <- c.downField("fields").as[List[String]]
      eventType      <- c.downField("event_type").as[String]
      destinationIds <- c.downField("destination_ids").as[List[String]]
      samplingRate   <- c.downField("sampling_rate").as[Double]
    } yield EventStream(
      id,
      uri,
      createdAt,
      metadata,
      description,
      fields,
      eventType,
      destinationIds,
      samplingRate
    )
}
