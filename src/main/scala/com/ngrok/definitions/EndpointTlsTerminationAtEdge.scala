package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointTlsTerminationAtEdge]] resource.
  *
  * @constructor create a new EndpointTlsTerminationAtEdge.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param minVersion The minimum TLS version used for termination and advertised to the client during the TLS handshake. if unspecified, ngrok will choose an industry-safe default. This value must be null if <code>terminate_at</code> is set to <code>upstream</code>.
  */
final case class EndpointTlsTerminationAtEdge(
  enabled: Option[Boolean] = None,
  minVersion: Option[String] = None
)

object EndpointTlsTerminationAtEdge {
  implicit val encodeEndpointTlsTerminationAtEdge: io.circe.Encoder[EndpointTlsTerminationAtEdge] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        value.minVersion.map(_.asJson).map(("min_version", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointTlsTerminationAtEdge: io.circe.Decoder[EndpointTlsTerminationAtEdge] =
    (c: io.circe.HCursor) =>
      for {
        enabled    <- c.downField("enabled").as[Option[Boolean]]
        minVersion <- c.downField("min_version").as[Option[String]]
      } yield EndpointTlsTerminationAtEdge(
        enabled,
        minVersion
      )
}
