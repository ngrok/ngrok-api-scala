package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedAddr]] resource.
  *
  * @constructor create a new ReservedAddr.
  * @param id unique reserved address resource identifier
  * @param uri URI of the reserved address API resource
  * @param createdAt timestamp when the reserved address was created, RFC 3339 format
  * @param description human-readable description of what this reserved address will be used for
  * @param metadata arbitrary user-defined machine-readable data of this reserved address. Optional, max 4096 bytes.
  * @param addr hostname:port of the reserved address that was assigned at creation time
  * @param region reserve the address in this geographic ngrok datacenter. Optional, default is us. (au, eu, ap, us, jp, in, sa)
  * @param endpointConfiguration object reference to the endpoint configuration that will be applied to traffic to this address
  */
final case class ReservedAddr(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  addr: String,
  region: String,
  endpointConfiguration: Option[Ref] = None
)

object ReservedAddr {
  implicit val encodeReservedAddr: io.circe.Encoder[ReservedAddr] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("addr", value.addr.asJson)),
      Option(("region", value.region.asJson)),
      value.endpointConfiguration.map(_.asJson).map(("endpoint_configuration", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeReservedAddr: io.circe.Decoder[ReservedAddr] = (c: io.circe.HCursor) =>
    for {
      id                    <- c.downField("id").as[String]
      uri                   <- c.downField("uri").as[java.net.URI]
      createdAt             <- c.downField("created_at").as[java.time.OffsetDateTime]
      description           <- c.downField("description").as[String]
      metadata              <- c.downField("metadata").as[String]
      addr                  <- c.downField("addr").as[String]
      region                <- c.downField("region").as[String]
      endpointConfiguration <- c.downField("endpoint_configuration").as[Option[Ref]]
    } yield ReservedAddr(
      id,
      uri,
      createdAt,
      description,
      metadata,
      addr,
      region,
      endpointConfiguration
    )
}
