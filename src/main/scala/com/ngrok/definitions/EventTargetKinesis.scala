package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventTargetKinesis]] resource.
  *
  * @constructor create a new EventTargetKinesis.
  * @param auth Configuration for how to authenticate into your AWS account. Exactly one of <code>role</code> or <code>creds</code> should be configured.
  * @param streamArn An Amazon Resource Name specifying the Kinesis stream to deposit events into.
  */
final case class EventTargetKinesis(
  auth: AwsAuth,
  streamArn: String
)

object EventTargetKinesis {
  implicit val encodeEventTargetKinesis: io.circe.Encoder[EventTargetKinesis] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("auth", value.auth.asJson)),
        Option(("stream_arn", value.streamArn.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventTargetKinesis: io.circe.Decoder[EventTargetKinesis] = (c: io.circe.HCursor) =>
    for {
      auth      <- c.downField("auth").as[AwsAuth]
      streamArn <- c.downField("stream_arn").as[String]
    } yield EventTargetKinesis(
      auth,
      streamArn
    )
}
