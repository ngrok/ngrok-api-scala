/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TunnelSession]] resource.
  *
  * @constructor create a new TunnelSession.
  * @param agentVersion version of the ngrok agent that started this ngrok tunnel session
  * @param credential reference to the tunnel credential or ssh credential used by the ngrok agent to start this tunnel session
  * @param id unique tunnel session resource identifier
  * @param ip source ip address of the tunnel session
  * @param metadata arbitrary user-defined data specified in the metadata property in the ngrok configuration file. See the metadata configuration option
  * @param os operating system of the host the ngrok agent is running on
  * @param region the ngrok region identifier in which this tunnel session was started
  * @param startedAt time when the tunnel session first connected to the ngrok servers
  * @param transport the transport protocol used to start the tunnel session. Either <code>ngrok/v2</code> or <code>ssh</code>
  * @param uri URI to the API resource of the tunnel session
  */
final case class TunnelSession(
  agentVersion: String,
  credential: Ref,
  id: String,
  ip: String,
  metadata: String,
  os: String,
  region: String,
  startedAt: java.time.OffsetDateTime,
  transport: String,
  uri: java.net.URI
)

object TunnelSession {
  implicit val encodeTunnelSession: io.circe.Encoder[TunnelSession] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("agent_version", value.agentVersion.asJson)),
        Option(("credential", value.credential.asJson)),
        Option(("id", value.id.asJson)),
        Option(("ip", value.ip.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("os", value.os.asJson)),
        Option(("region", value.region.asJson)),
        Option(("started_at", value.startedAt.asJson)),
        Option(("transport", value.transport.asJson)),
        Option(("uri", value.uri.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTunnelSession: io.circe.Decoder[TunnelSession] = (c: io.circe.HCursor) =>
    for {
      agentVersion <- c.downField("agent_version").as[String]
      credential   <- c.downField("credential").as[Ref]
      id           <- c.downField("id").as[String]
      ip           <- c.downField("ip").as[String]
      metadata     <- c.downField("metadata").as[String]
      os           <- c.downField("os").as[String]
      region       <- c.downField("region").as[String]
      startedAt    <- c.downField("started_at").as[java.time.OffsetDateTime]
      transport    <- c.downField("transport").as[String]
      uri          <- c.downField("uri").as[java.net.URI]
    } yield TunnelSession(
      agentVersion,
      credential,
      id,
      ip,
      metadata,
      os,
      region,
      startedAt,
      transport,
      uri
    )
}
