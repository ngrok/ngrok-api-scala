package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointWebsocketTcpConverter]] resource.
  *
  * @constructor create a new EndpointWebsocketTcpConverter.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  */
final case class EndpointWebsocketTcpConverter(
  enabled: Option[Boolean] = None
)

object EndpointWebsocketTcpConverter {
  implicit val encodeEndpointWebsocketTcpConverter: io.circe.Encoder[EndpointWebsocketTcpConverter] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointWebsocketTcpConverter: io.circe.Decoder[EndpointWebsocketTcpConverter] =
    (c: io.circe.HCursor) =>
      for {
        enabled <- c.downField("enabled").as[Option[Boolean]]
      } yield EndpointWebsocketTcpConverter(
        enabled
      )
}
