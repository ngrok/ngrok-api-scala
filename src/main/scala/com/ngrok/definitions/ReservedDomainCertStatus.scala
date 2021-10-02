package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomainCertStatus]] resource.
  *
  * @constructor create a new ReservedDomainCertStatus.
  * @param renewsAt timestamp when the next renewal will be requested, RFC 3339 format
  * @param provisioningJob status of the certificate provisioning job, or null if the certificiate isn't being provisioned or renewed
  */
final case class ReservedDomainCertStatus(
  renewsAt: Option[java.time.OffsetDateTime] = None,
  provisioningJob: Option[ReservedDomainCertJob] = None
)

object ReservedDomainCertStatus {
  implicit val encodeReservedDomainCertStatus: io.circe.Encoder[ReservedDomainCertStatus] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.renewsAt.map(_.asJson).map(("renews_at", _)),
        value.provisioningJob.map(_.asJson).map(("provisioning_job", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomainCertStatus: io.circe.Decoder[ReservedDomainCertStatus] = (c: io.circe.HCursor) =>
    for {
      renewsAt        <- c.downField("renews_at").as[Option[java.time.OffsetDateTime]]
      provisioningJob <- c.downField("provisioning_job").as[Option[ReservedDomainCertJob]]
    } yield ReservedDomainCertStatus(
      renewsAt,
      provisioningJob
    )
}
