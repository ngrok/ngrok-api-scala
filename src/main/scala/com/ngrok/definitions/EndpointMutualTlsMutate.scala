/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointMutualTlsMutate]] resource.
  *
  * @constructor create a new EndpointMutualTlsMutate.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param certificateAuthorityIds list of certificate authorities that will be used to validate the TLS client certificate presented by the initiator of the TLS connection
  */
final case class EndpointMutualTlsMutate(
  enabled: Option[Boolean] = None,
  certificateAuthorityIds: List[String]
)

object EndpointMutualTlsMutate {
  implicit val encodeEndpointMutualTlsMutate: io.circe.Encoder[EndpointMutualTlsMutate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("certificate_authority_ids", value.certificateAuthorityIds.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointMutualTlsMutate: io.circe.Decoder[EndpointMutualTlsMutate] = (c: io.circe.HCursor) =>
    for {
      enabled                 <- c.downField("enabled").as[Option[Boolean]]
      certificateAuthorityIds <- c.downField("certificate_authority_ids").as[Option[List[String]]]
    } yield EndpointMutualTlsMutate(
      enabled,
      certificateAuthorityIds.getOrElse(List.empty)
    )
}
