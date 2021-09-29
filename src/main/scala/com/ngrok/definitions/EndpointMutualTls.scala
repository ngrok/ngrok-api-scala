package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointMutualTls]] resource.
  *
  * @constructor create a new EndpointMutualTls.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param certificateAuthorities PEM-encoded CA certificates that will be used to validate. Multiple CAs may be provided by concatenating them together.
  */
final case class EndpointMutualTls(
  enabled: Option[Boolean],
  certificateAuthorities: List[Ref]
)

object EndpointMutualTls {
  implicit val encodeEndpointMutualTls: io.circe.Encoder[EndpointMutualTls] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("certificate_authorities", value.certificateAuthorities.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointMutualTls: io.circe.Decoder[EndpointMutualTls] = (c: io.circe.HCursor) =>
    for {
      enabled                <- c.downField("enabled").as[Option[Boolean]]
      certificateAuthorities <- c.downField("certificate_authorities").as[List[Ref]]
    } yield EndpointMutualTls(
      enabled,
      certificateAuthorities
    )
}
