package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventTargetFirehose]] resource.
  *
  * @constructor create a new EventTargetFirehose.
  * @param auth Configuration for how to authenticate into your AWS account. Exactly one of <code>role</code> or <code>creds</code> should be configured.
  * @param deliveryStreamArn An Amazon Resource Name specifying the Firehose delivery stream to deposit events into.
  */
final case class EventTargetFirehose(
  auth: AwsAuth,
  deliveryStreamArn: String
)

object EventTargetFirehose {
  implicit val encodeEventTargetFirehose: io.circe.Encoder[EventTargetFirehose] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("auth", value.auth.asJson)),
        Option(("delivery_stream_arn", value.deliveryStreamArn.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventTargetFirehose: io.circe.Decoder[EventTargetFirehose] = (c: io.circe.HCursor) =>
    for {
      auth              <- c.downField("auth").as[AwsAuth]
      deliveryStreamArn <- c.downField("delivery_stream_arn").as[String]
    } yield EventTargetFirehose(
      auth,
      deliveryStreamArn
    )
}
