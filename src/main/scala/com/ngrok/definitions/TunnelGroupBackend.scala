/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TunnelGroupBackend]] resource.
  *
  * @constructor create a new TunnelGroupBackend.
  * @param id unique identifier for this TunnelGroup backend
  * @param uri URI of the TunnelGroupBackend API resource
  * @param createdAt timestamp when the backend was created, RFC 3339 format
  * @param description human-readable description of this backend. Optional
  * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
  * @param labels labels to watch for tunnels on, e.g. app-&gt;foo, dc-&gt;bar
  * @param tunnels tunnels matching this backend
  */
final case class TunnelGroupBackend(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  labels: Map[String, String],
  tunnels: List[Ref]
)

object TunnelGroupBackend {
  implicit val encodeTunnelGroupBackend: io.circe.Encoder[TunnelGroupBackend] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("labels", value.labels.asJson)),
        Option(("tunnels", value.tunnels.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTunnelGroupBackend: io.circe.Decoder[TunnelGroupBackend] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      labels      <- c.downField("labels").as[Option[Map[String, String]]]
      tunnels     <- c.downField("tunnels").as[Option[List[Ref]]]
    } yield TunnelGroupBackend(
      id,
      uri,
      createdAt,
      description,
      metadata,
      labels.getOrElse(Map.empty),
      tunnels.getOrElse(List.empty)
    )
}
