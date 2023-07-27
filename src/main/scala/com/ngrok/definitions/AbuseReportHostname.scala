/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AbuseReportHostname]] resource.
  *
  * @constructor create a new AbuseReportHostname.
  * @param hostname the hostname ngrok has parsed out of one of the reported URLs in this abuse report
  * @param status indicates what action ngrok has taken against the hostname. one of <code>PENDING</code>, <code>BANNED</code>, <code>UNBANNED</code>, or <code>IGNORE</code>
  */
final case class AbuseReportHostname(
  hostname: String,
  status: String
)

object AbuseReportHostname {
  implicit val encodeAbuseReportHostname: io.circe.Encoder[AbuseReportHostname] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("hostname", value.hostname.asJson)),
        Option(("status", value.status.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeAbuseReportHostname: io.circe.Decoder[AbuseReportHostname] = (c: io.circe.HCursor) =>
    for {
      hostname <- c.downField("hostname").as[String]
      status   <- c.downField("status").as[String]
    } yield AbuseReportHostname(
      hostname,
      status
    )
}
