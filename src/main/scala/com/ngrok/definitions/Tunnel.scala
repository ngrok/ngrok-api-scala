/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[Tunnel]] resource.
  *
  * @constructor create a new Tunnel.
  * @param id unique tunnel resource identifier
  * @param publicUrl URL of the ephemeral tunnel&#39;s public endpoint
  * @param startedAt timestamp when the tunnel was initiated in RFC 3339 format
  * @param metadata user-supplied metadata for the tunnel defined in the ngrok configuration file. See the tunnel <a href="/ngrok-agent/config#common-tunnel-configuration-properties">metadata configuration option</a> In API version 0, this value was instead pulled from the top-level <a href="/ngrok-agent/config#metadata">metadata configuration option</a>.
  * @param proto tunnel protocol for ephemeral tunnels. one of <code>http</code>, <code>https</code>, <code>tcp</code> or <code>tls</code>
  * @param region identifier of tune region where the tunnel is running
  * @param tunnelSession reference object pointing to the tunnel session on which this tunnel was started
  * @param endpoint the ephemeral endpoint this tunnel is associated with, if this is an agent-initiated tunnel
  * @param labels the labels the tunnel group backends will match against, if this is a backend tunnel
  * @param backends tunnel group backends served by this backend tunnel
  * @param forwardsTo upstream address the ngrok agent forwards traffic over this tunnel to. this may be expressed as a URL or a network address.
  */
final case class Tunnel(
  id: String,
  publicUrl: java.net.URI,
  startedAt: java.time.OffsetDateTime,
  metadata: String,
  proto: String,
  region: String,
  tunnelSession: Ref,
  endpoint: Option[Ref] = None,
  labels: Option[Map[String, String]] = None,
  backends: Option[List[Ref]] = None,
  forwardsTo: String
)

object Tunnel {
  implicit val encodeTunnel: io.circe.Encoder[Tunnel] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("public_url", value.publicUrl.asJson)),
      Option(("started_at", value.startedAt.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("proto", value.proto.asJson)),
      Option(("region", value.region.asJson)),
      Option(("tunnel_session", value.tunnelSession.asJson)),
      value.endpoint.map(_.asJson).map(("endpoint", _)),
      if (value.labels.isEmpty) None else Option(("labels", value.labels.asJson)),
      if (value.backends.isEmpty) None else Option(("backends", value.backends.asJson)),
      Option(("forwards_to", value.forwardsTo.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeTunnel: io.circe.Decoder[Tunnel] = (c: io.circe.HCursor) =>
    for {
      id            <- c.downField("id").as[String]
      publicUrl     <- c.downField("public_url").as[java.net.URI]
      startedAt     <- c.downField("started_at").as[java.time.OffsetDateTime]
      metadata      <- c.downField("metadata").as[String]
      proto         <- c.downField("proto").as[String]
      region        <- c.downField("region").as[String]
      tunnelSession <- c.downField("tunnel_session").as[Ref]
      endpoint      <- c.downField("endpoint").as[Option[Ref]]
      labels        <- c.downField("labels").as[Option[Map[String, String]]]
      backends      <- c.downField("backends").as[Option[List[Ref]]]
      forwardsTo    <- c.downField("forwards_to").as[String]
    } yield Tunnel(
      id,
      publicUrl,
      startedAt,
      metadata,
      proto,
      region,
      tunnelSession,
      endpoint,
      labels,
      backends,
      forwardsTo
    )
}
