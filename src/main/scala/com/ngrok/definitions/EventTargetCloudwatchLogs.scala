package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventTargetCloudwatchLogs]] resource.
  *
  * @constructor create a new EventTargetCloudwatchLogs.
  * @param auth Configuration for how to authenticate into your AWS account. Exactly one of <code>role</code> or <code>creds</code> should be configured.
  * @param logGroupArn An Amazon Resource Name specifying the CloudWatch Logs group to deposit events into.
  */
final case class EventTargetCloudwatchLogs(
  auth: AwsAuth,
  logGroupArn: String
)

object EventTargetCloudwatchLogs {
  implicit val encodeEventTargetCloudwatchLogs: io.circe.Encoder[EventTargetCloudwatchLogs] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("auth", value.auth.asJson)),
        Option(("log_group_arn", value.logGroupArn.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventTargetCloudwatchLogs: io.circe.Decoder[EventTargetCloudwatchLogs] = (c: io.circe.HCursor) =>
    for {
      auth        <- c.downField("auth").as[AwsAuth]
      logGroupArn <- c.downField("log_group_arn").as[String]
    } yield EventTargetCloudwatchLogs(
      auth,
      logGroupArn
    )
}
