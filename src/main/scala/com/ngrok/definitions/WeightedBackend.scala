/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[WeightedBackend]] resource.
  *
  * @constructor create a new WeightedBackend.
  * @param id unique identifier for this Weighted backend
  * @param uri URI of the WeightedBackend API resource
  * @param createdAt timestamp when the backend was created, RFC 3339 format
  * @param description human-readable description of this backend. Optional
  * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
  * @param backends the ids of the child backends to their weights [0-10000]
  */
final case class WeightedBackend(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  backends: Map[String, Long]
)

object WeightedBackend {
  implicit val encodeWeightedBackend: io.circe.Encoder[WeightedBackend] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("backends", value.backends.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeWeightedBackend: io.circe.Decoder[WeightedBackend] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      backends    <- c.downField("backends").as[Option[Map[String, Long]]]
    } yield WeightedBackend(
      id,
      uri,
      createdAt,
      description,
      metadata,
      backends.getOrElse(Map.empty)
    )
}
