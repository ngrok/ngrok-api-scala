package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[Endpoint]] resource.
  *
  * @constructor create a new Endpoint.
  * @param id unique endpoint resource identifier
  * @param region identifier of the region this endpoint belongs to
  * @param createdAt timestamp when the endpoint was created in RFC 3339 format
  * @param updatedAt timestamp when the endpoint was updated in RFC 3339 format
  * @param publicUrl URL of the hostport served by this endpoint
  * @param proto protocol served by this endpoint. one of <code>http</code>, <code>https</code>, <code>tcp</code>, or <code>tls</code>
  * @param hostport hostport served by this endpoint (hostname:port)
  * @param `type` whether the endpoint is <code>ephemeral</code> (served directly by an agent-initiated tunnel) or <code>edge</code> (served by an edge)
  * @param metadata user-supplied metadata of the associated tunnel or edge object
  * @param domain the domain reserved for this endpoint
  * @param tcpAddr the address reserved for this endpoint
  * @param tunnel the tunnel serving requests to this endpoint, if this is an ephemeral endpoint
  * @param edge the edge serving requests to this endpoint, if this is an edge endpoint
  */
final case class Endpoint(
  id: String,
  region: String,
  createdAt: java.time.OffsetDateTime,
  updatedAt: java.time.OffsetDateTime,
  publicUrl: java.net.URI,
  proto: String,
  hostport: String,
  `type`: String,
  metadata: String,
  domain: Option[Ref] = None,
  tcpAddr: Option[Ref] = None,
  tunnel: Option[Ref] = None,
  edge: Option[Ref] = None
)

object Endpoint {
  implicit val encodeEndpoint: io.circe.Encoder[Endpoint] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("region", value.region.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("updated_at", value.updatedAt.asJson)),
      Option(("public_url", value.publicUrl.asJson)),
      Option(("proto", value.proto.asJson)),
      Option(("hostport", value.hostport.asJson)),
      Option(("type", value.`type`.asJson)),
      Option(("metadata", value.metadata.asJson)),
      value.domain.map(_.asJson).map(("domain", _)),
      value.tcpAddr.map(_.asJson).map(("tcp_addr", _)),
      value.tunnel.map(_.asJson).map(("tunnel", _)),
      value.edge.map(_.asJson).map(("edge", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEndpoint: io.circe.Decoder[Endpoint] = (c: io.circe.HCursor) =>
    for {
      id        <- c.downField("id").as[String]
      region    <- c.downField("region").as[String]
      createdAt <- c.downField("created_at").as[java.time.OffsetDateTime]
      updatedAt <- c.downField("updated_at").as[java.time.OffsetDateTime]
      publicUrl <- c.downField("public_url").as[java.net.URI]
      proto     <- c.downField("proto").as[String]
      hostport  <- c.downField("hostport").as[String]
      `type`    <- c.downField("type").as[String]
      metadata  <- c.downField("metadata").as[String]
      domain    <- c.downField("domain").as[Option[Ref]]
      tcpAddr   <- c.downField("tcp_addr").as[Option[Ref]]
      tunnel    <- c.downField("tunnel").as[Option[Ref]]
      edge      <- c.downField("edge").as[Option[Ref]]
    } yield Endpoint(
      id,
      region,
      createdAt,
      updatedAt,
      publicUrl,
      proto,
      hostport,
      `type`,
      metadata,
      domain,
      tcpAddr,
      tunnel,
      edge
    )
}
