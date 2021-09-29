package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomainCertJob]] resource.
  *
  * @constructor create a new ReservedDomainCertJob.
  * @param errorCode if present, an error code indicating why provisioning is failing. It may be either a temporary condition (INTERNAL_ERROR), or a permanent one the user must correct (DNS_ERROR).
  * @param msg a message describing the current status or error
  * @param startedAt timestamp when the provisioning job started, RFC 3339 format
  * @param retriesAt timestamp when the provisioning job will be retried
  * @param nsTargets if present, indicates the dns nameservers that the user must configure to complete the provisioning process of a wildcard certificate
  */
final case class ReservedDomainCertJob(
  errorCode: Option[String],
  msg: String,
  startedAt: java.time.OffsetDateTime,
  retriesAt: Option[java.time.OffsetDateTime],
  nsTargets: List[ReservedDomainCertNsTarget]
)

object ReservedDomainCertJob {
  implicit val encodeReservedDomainCertJob: io.circe.Encoder[ReservedDomainCertJob] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.errorCode.map(_.asJson).map(("error_code", _)),
        Option(("msg", value.msg.asJson)),
        Option(("started_at", value.startedAt.asJson)),
        value.retriesAt.map(_.asJson).map(("retries_at", _)),
        Option(("ns_targets", value.nsTargets.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomainCertJob: io.circe.Decoder[ReservedDomainCertJob] = (c: io.circe.HCursor) =>
    for {
      errorCode <- c.downField("error_code").as[Option[String]]
      msg       <- c.downField("msg").as[String]
      startedAt <- c.downField("started_at").as[java.time.OffsetDateTime]
      retriesAt <- c.downField("retries_at").as[Option[java.time.OffsetDateTime]]
      nsTargets <- c.downField("ns_targets").as[List[ReservedDomainCertNsTarget]]
    } yield ReservedDomainCertJob(
      errorCode,
      msg,
      startedAt,
      retriesAt,
      nsTargets
    )
}
