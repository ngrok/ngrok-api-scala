package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomainCertNsTarget]] resource.
  *
  * @constructor create a new ReservedDomainCertNsTarget.
  * @param zone the zone that the nameservers need to be applied to
  * @param nameservers the nameservers the user must add
  */
final case class ReservedDomainCertNsTarget(
  zone: String,
  nameservers: List[String]
)

object ReservedDomainCertNsTarget {
  implicit val encodeReservedDomainCertNsTarget: io.circe.Encoder[ReservedDomainCertNsTarget] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("zone", value.zone.asJson)),
        Option(("nameservers", value.nameservers.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomainCertNsTarget: io.circe.Decoder[ReservedDomainCertNsTarget] = (c: io.circe.HCursor) =>
    for {
      zone        <- c.downField("zone").as[String]
      nameservers <- c.downField("nameservers").as[List[String]]
    } yield ReservedDomainCertNsTarget(
      zone,
      nameservers
    )
}
