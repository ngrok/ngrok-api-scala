package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointIpPolicyMutate]] resource.
  *
  * @constructor create a new EndpointIpPolicyMutate.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param ipPolicyIds list of all IP policies that will be used to check if a source IP is allowed access to the endpoint
  */
final case class EndpointIpPolicyMutate(
  enabled: Option[Boolean] = None,
  ipPolicyIds: List[String]
)

object EndpointIpPolicyMutate {
  implicit val encodeEndpointIpPolicyMutate: io.circe.Encoder[EndpointIpPolicyMutate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("ip_policy_ids", value.ipPolicyIds.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointIpPolicyMutate: io.circe.Decoder[EndpointIpPolicyMutate] = (c: io.circe.HCursor) =>
    for {
      enabled     <- c.downField("enabled").as[Option[Boolean]]
      ipPolicyIds <- c.downField("ip_policy_ids").as[Option[List[String]]]
    } yield EndpointIpPolicyMutate(
      enabled,
      ipPolicyIds.getOrElse(List.empty)
    )
}
