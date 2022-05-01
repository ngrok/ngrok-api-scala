package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AbuseReport]] resource.
  *
  * @constructor create a new AbuseReport.
  * @param id ID of the abuse report
  * @param uri URI of the abuse report API resource
  * @param createdAt timestamp that the abuse report record was created in RFC 3339 format
  * @param urls a list of URLs containing suspected abusive content
  * @param metadata arbitrary user-defined data about this abuse report. Optional, max 4096 bytes.
  * @param status Indicates whether ngrok has processed the abuse report. one of <code>PENDING</code>, <code>PROCESSED</code>, or <code>PARTIALLY_PROCESSED</code>
  * @param hostnames an array of hostname statuses related to the report
  */
final case class AbuseReport(
  id: String,
  uri: java.net.URI,
  createdAt: java.time.OffsetDateTime,
  urls: List[java.net.URI],
  metadata: String,
  status: String,
  hostnames: List[AbuseReportHostname]
)

object AbuseReport {
  implicit val encodeAbuseReport: io.circe.Encoder[AbuseReport] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("urls", value.urls.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("status", value.status.asJson)),
      Option(("hostnames", value.hostnames.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeAbuseReport: io.circe.Decoder[AbuseReport] = (c: io.circe.HCursor) =>
    for {
      id        <- c.downField("id").as[String]
      uri       <- c.downField("uri").as[java.net.URI]
      createdAt <- c.downField("created_at").as[java.time.OffsetDateTime]
      urls      <- c.downField("urls").as[Option[List[java.net.URI]]]
      metadata  <- c.downField("metadata").as[String]
      status    <- c.downField("status").as[String]
      hostnames <- c.downField("hostnames").as[Option[List[AbuseReportHostname]]]
    } yield AbuseReport(
      id,
      uri,
      createdAt,
      urls.getOrElse(List.empty),
      metadata,
      status,
      hostnames.getOrElse(List.empty)
    )
}
