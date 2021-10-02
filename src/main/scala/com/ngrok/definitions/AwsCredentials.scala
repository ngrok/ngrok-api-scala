package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AwsCredentials]] resource.
  *
  * @constructor create a new AwsCredentials.
  * @param awsAccessKeyId The ID portion of an AWS access key.
  * @param awsSecretAccessKey The secret portion of an AWS access key.
  */
final case class AwsCredentials(
  awsAccessKeyId: String,
  awsSecretAccessKey: Option[String] = None
)

object AwsCredentials {
  implicit val encodeAwsCredentials: io.circe.Encoder[AwsCredentials] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("aws_access_key_id", value.awsAccessKeyId.asJson)),
        value.awsSecretAccessKey.map(_.asJson).map(("aws_secret_access_key", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeAwsCredentials: io.circe.Decoder[AwsCredentials] = (c: io.circe.HCursor) =>
    for {
      awsAccessKeyId     <- c.downField("aws_access_key_id").as[String]
      awsSecretAccessKey <- c.downField("aws_secret_access_key").as[Option[String]]
    } yield AwsCredentials(
      awsAccessKeyId,
      awsSecretAccessKey
    )
}
