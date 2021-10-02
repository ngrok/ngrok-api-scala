package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointLogging]] resource.
  *
  * @constructor create a new EndpointLogging.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param eventStreams list of all EventStreams that will be used to configure and export this endpoint's logs
  */
final case class EndpointLogging(
  enabled: Option[Boolean] = None,
  eventStreams: List[Ref]
)

object EndpointLogging {
  implicit val encodeEndpointLogging: io.circe.Encoder[EndpointLogging] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("event_streams", value.eventStreams.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointLogging: io.circe.Decoder[EndpointLogging] = (c: io.circe.HCursor) =>
    for {
      enabled      <- c.downField("enabled").as[Option[Boolean]]
      eventStreams <- c.downField("event_streams").as[List[Ref]]
    } yield EndpointLogging(
      enabled,
      eventStreams
    )
}
