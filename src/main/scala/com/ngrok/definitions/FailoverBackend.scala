package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[FailoverBackend]] resource.
  *
  * @constructor create a new FailoverBackend.
  * @param id unique identifier for this Failover backend
  * @param uri URI of the FailoverBackend API resource
  * @param createdAt timestamp when the backend was created, RFC 3339 format
  * @param description human-readable description of this backend. Optional
  * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
  * @param backends the ids of the child backends in order
  */
final case class FailoverBackend(
  id: String,
  uri: String,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  backends: List[String]
)

object FailoverBackend {
  implicit val encodeFailoverBackend: io.circe.Encoder[FailoverBackend] =
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

  implicit val decodeFailoverBackend: io.circe.Decoder[FailoverBackend] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[String]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      backends    <- c.downField("backends").as[List[String]]
    } yield FailoverBackend(
      id,
      uri,
      createdAt,
      description,
      metadata,
      backends
    )
}
