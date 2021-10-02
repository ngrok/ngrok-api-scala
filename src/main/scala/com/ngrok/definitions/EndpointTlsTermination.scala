package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointTlsTermination]] resource.
  *
  * @constructor create a new EndpointTlsTermination.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param terminateAt <code>edge</code> if the ngrok edge should terminate TLS traffic, <code>upstream</code> if TLS traffic should be passed through to the upstream ngrok agent / application server for termination. if <code>upstream</code> is chosen, most other modules will be disallowed because they rely on the ngrok edge being able to access the underlying traffic.
  * @param minVersion The minimum TLS version used for termination and advertised to the client during the TLS handshake. if unspecified, ngrok will choose an industry-safe default. This value must be null if <code>terminate_at</code> is set to <code>upstream</code>.
  */
final case class EndpointTlsTermination(
  enabled: Option[Boolean] = None,
  terminateAt: String,
  minVersion: Option[String] = None
)

object EndpointTlsTermination {
  implicit val encodeEndpointTlsTermination: io.circe.Encoder[EndpointTlsTermination] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("terminate_at", value.terminateAt.asJson)),
        value.minVersion.map(_.asJson).map(("min_version", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointTlsTermination: io.circe.Decoder[EndpointTlsTermination] = (c: io.circe.HCursor) =>
    for {
      enabled     <- c.downField("enabled").as[Option[Boolean]]
      terminateAt <- c.downField("terminate_at").as[String]
      minVersion  <- c.downField("min_version").as[Option[String]]
    } yield EndpointTlsTermination(
      enabled,
      terminateAt,
      minVersion
    )
}
