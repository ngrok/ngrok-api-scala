package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[HttpsEdge]] resource.
  *
  * @constructor create a new HttpsEdge.
  * @param id unique identifier of this edge
  * @param description human-readable description of what this edge will be used for; optional, max 255 bytes.
  * @param metadata arbitrary user-defined machine-readable data of this edge; optional, max 4096 bytes.
  * @param createdAt timestamp when the edge configuration was created, RFC 3339 format
  * @param uri URI of the edge API resource
  * @param hostports hostports served by this edge
  * @param mutualTls edge modules
  * @param tlsTermination the value of the <code>tls_termination</code> parameter as a [[EndpointTlsTermination]]
  * @param routes routes
  */
final case class HttpsEdge(
  id: String,
  description: String,
  metadata: String,
  createdAt: java.time.OffsetDateTime,
  uri: java.net.URI,
  hostports: Option[List[String]] = None,
  mutualTls: Option[EndpointMutualTls] = None,
  tlsTermination: Option[EndpointTlsTermination] = None,
  routes: List[HttpsEdgeRoute]
)

object HttpsEdge {
  implicit val encodeHttpsEdge: io.circe.Encoder[HttpsEdge] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("uri", value.uri.asJson)),
      if (value.hostports.isEmpty) None else Option(("hostports", value.hostports.asJson)),
      value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
      value.tlsTermination.map(_.asJson).map(("tls_termination", _)),
      Option(("routes", value.routes.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeHttpsEdge: io.circe.Decoder[HttpsEdge] = (c: io.circe.HCursor) =>
    for {
      id             <- c.downField("id").as[String]
      description    <- c.downField("description").as[String]
      metadata       <- c.downField("metadata").as[String]
      createdAt      <- c.downField("created_at").as[java.time.OffsetDateTime]
      uri            <- c.downField("uri").as[java.net.URI]
      hostports      <- c.downField("hostports").as[Option[List[String]]]
      mutualTls      <- c.downField("mutual_tls").as[Option[EndpointMutualTls]]
      tlsTermination <- c.downField("tls_termination").as[Option[EndpointTlsTermination]]
      routes         <- c.downField("routes").as[Option[List[HttpsEdgeRoute]]]
    } yield HttpsEdge(
      id,
      description,
      metadata,
      createdAt,
      uri,
      hostports,
      mutualTls,
      tlsTermination,
      routes.getOrElse(List.empty)
    )
}
