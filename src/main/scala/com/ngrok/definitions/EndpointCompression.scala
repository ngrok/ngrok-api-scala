package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointCompression]] resource.
  *
  * @constructor create a new EndpointCompression.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  */
final case class EndpointCompression(
  enabled: Option[Boolean] = None
)

object EndpointCompression {
  implicit val encodeEndpointCompression: io.circe.Encoder[EndpointCompression] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointCompression: io.circe.Decoder[EndpointCompression] = (c: io.circe.HCursor) =>
    for {
      enabled <- c.downField("enabled").as[Option[Boolean]]
    } yield EndpointCompression(
      enabled
    )
}
