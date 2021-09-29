package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventTarget]] resource.
  *
  * @constructor create a new EventTarget.
  * @param firehose Configuration used to send events to Amazon Kinesis Data Firehose.
  * @param kinesis Configuration used to send events to Amazon Kinesis.
  * @param cloudwatchLogs Configuration used to send events to Amazon CloudWatch Logs.
  */
final case class EventTarget(
  firehose: Option[EventTargetFirehose],
  kinesis: Option[EventTargetKinesis],
  cloudwatchLogs: Option[EventTargetCloudwatchLogs]
)

object EventTarget {
  implicit val encodeEventTarget: io.circe.Encoder[EventTarget] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.firehose.map(_.asJson).map(("firehose", _)),
      value.kinesis.map(_.asJson).map(("kinesis", _)),
      value.cloudwatchLogs.map(_.asJson).map(("cloudwatch_logs", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEventTarget: io.circe.Decoder[EventTarget] = (c: io.circe.HCursor) =>
    for {
      firehose       <- c.downField("firehose").as[Option[EventTargetFirehose]]
      kinesis        <- c.downField("kinesis").as[Option[EventTargetKinesis]]
      cloudwatchLogs <- c.downField("cloudwatch_logs").as[Option[EventTargetCloudwatchLogs]]
    } yield EventTarget(
      firehose,
      kinesis,
      cloudwatchLogs
    )
}
