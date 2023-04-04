package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointIpPolicy]] resource.
  *
  * @constructor create a new EndpointIpPolicy.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param ipPolicies list of all IP policies that will be used to check if a source IP is allowed access to the endpoint
  */
final case class EndpointIpPolicy(
  enabled: Option[Boolean] = None,
  ipPolicies: List[Ref]
)

object EndpointIpPolicy {
  implicit val encodeEndpointIpPolicy: io.circe.Encoder[EndpointIpPolicy] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("ip_policies", value.ipPolicies.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointIpPolicy: io.circe.Decoder[EndpointIpPolicy] = (c: io.circe.HCursor) =>
    for {
      enabled    <- c.downField("enabled").as[Option[Boolean]]
      ipPolicies <- c.downField("ip_policies").as[Option[List[Ref]]]
    } yield EndpointIpPolicy(
      enabled,
      ipPolicies.getOrElse(List.empty)
    )
}
