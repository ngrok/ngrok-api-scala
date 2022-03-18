package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointBackend]] resource.
  *
  * @constructor create a new EndpointBackend.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param backend backend to be used to back this endpoint
  */
final case class EndpointBackend(
  enabled: Option[Boolean] = None,
  backend: Ref
)

object EndpointBackend {
  implicit val encodeEndpointBackend: io.circe.Encoder[EndpointBackend] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("backend", value.backend.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointBackend: io.circe.Decoder[EndpointBackend] = (c: io.circe.HCursor) =>
    for {
      enabled <- c.downField("enabled").as[Option[Boolean]]
      backend <- c.downField("backend").as[Ref]
    } yield EndpointBackend(
      enabled,
      backend
    )
}
