package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AwsRole]] resource.
  *
  * @constructor create a new AwsRole.
  * @param roleArn An ARN that specifies the role that ngrok should use to deliver to the configured target.
  */
final case class AwsRole(
  roleArn: String
)

object AwsRole {
  implicit val encodeAwsRole: io.circe.Encoder[AwsRole] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("role_arn", value.roleArn.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeAwsRole: io.circe.Decoder[AwsRole] = (c: io.circe.HCursor) =>
    for {
      roleArn <- c.downField("role_arn").as[String]
    } yield AwsRole(
      roleArn
    )
}
