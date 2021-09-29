package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventSubscription]] resource.
  *
  * @constructor create a new EventSubscription.
  * @param id Unique identifier for this Event Subscription.
  * @param uri URI of the Event Subscription API resource.
  * @param createdAt When the Event Subscription was created (RFC 3339 format).
  * @param metadata Arbitrary customer supplied information intended to be machine readable. Optional, max 4096 chars.
  * @param description Arbitrary customer supplied information intended to be human readable. Optional, max 255 chars.
  * @param sources Sources containing the types for which this event subscription will trigger
  * @param destinations Destinations to which these events will be sent
  */
final case class EventSubscription(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  metadata: String,
  description: String,
  sources: List[EventSource],
  destinations: List[Ref]
)

object EventSubscription {
  implicit val encodeEventSubscription: io.circe.Encoder[EventSubscription] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("description", value.description.asJson)),
        Option(("sources", value.sources.asJson)),
        Option(("destinations", value.destinations.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventSubscription: io.circe.Decoder[EventSubscription] = (c: io.circe.HCursor) =>
    for {
      id           <- c.downField("id").as[String]
      uri          <- c.downField("uri").as[java.net.URI]
      createdAt    <- c.downField("created_at").as[java.time.OffsetDateTime]
      metadata     <- c.downField("metadata").as[String]
      description  <- c.downField("description").as[String]
      sources      <- c.downField("sources").as[List[EventSource]]
      destinations <- c.downField("destinations").as[List[Ref]]
    } yield EventSubscription(
      id,
      uri,
      createdAt,
      metadata,
      description,
      sources,
      destinations
    )
}
