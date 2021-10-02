package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointLoggingMutate]] resource.
  *
  * @constructor create a new EndpointLoggingMutate.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param eventStreamIds list of all EventStreams that will be used to configure and export this endpoint's logs
  */
final case class EndpointLoggingMutate(
  enabled: Option[Boolean] = None,
  eventStreamIds: List[String]
)

object EndpointLoggingMutate {
  implicit val encodeEndpointLoggingMutate: io.circe.Encoder[EndpointLoggingMutate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("event_stream_ids", value.eventStreamIds.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointLoggingMutate: io.circe.Decoder[EndpointLoggingMutate] = (c: io.circe.HCursor) =>
    for {
      enabled        <- c.downField("enabled").as[Option[Boolean]]
      eventStreamIds <- c.downField("event_stream_ids").as[List[String]]
    } yield EndpointLoggingMutate(
      enabled,
      eventStreamIds
    )
}
