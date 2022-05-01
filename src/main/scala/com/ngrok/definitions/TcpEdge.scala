package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TcpEdge]] resource.
  *
  * @constructor create a new TcpEdge.
  * @param id unique identifier of this edge
  * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this edge. Optional, max 4096 bytes.
  * @param createdAt timestamp when the edge was created, RFC 3339 format
  * @param uri URI of the edge API resource
  * @param hostports hostports served by this edge
  * @param backend edge modules
  * @param ipRestriction the value of the <code>ip_restriction</code> parameter as a [[EndpointIpPolicy]]
  */
final case class TcpEdge(
  id: String,
  description: String,
  metadata: String,
  createdAt: java.time.OffsetDateTime,
  uri: java.net.URI,
  hostports: Option[List[String]] = None,
  backend: Option[EndpointBackend] = None,
  ipRestriction: Option[EndpointIpPolicy] = None
)

object TcpEdge {
  implicit val encodeTcpEdge: io.circe.Encoder[TcpEdge] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("uri", value.uri.asJson)),
      if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
      value.backend.map(_.asJson).map(("backend", _)),
      value.ipRestriction.map(_.asJson).map(("ip_restriction", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeTcpEdge: io.circe.Decoder[TcpEdge] = (c: io.circe.HCursor) =>
    for {
      id            <- c.downField("id").as[String]
      description   <- c.downField("description").as[String]
      metadata      <- c.downField("metadata").as[String]
      createdAt     <- c.downField("created_at").as[java.time.OffsetDateTime]
      uri           <- c.downField("uri").as[java.net.URI]
      hostports     <- c.downField("hostports").as[Option[List[String]]]
      backend       <- c.downField("backend").as[Option[EndpointBackend]]
      ipRestriction <- c.downField("ip_restriction").as[Option[EndpointIpPolicy]]
    } yield TcpEdge(
      id,
      description,
      metadata,
      createdAt,
      uri,
      hostports,
      backend,
      ipRestriction
    )
}
