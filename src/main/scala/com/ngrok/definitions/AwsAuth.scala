/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AwsAuth]] resource.
  *
  * @constructor create a new AwsAuth.
  * @param role A role for ngrok to assume on your behalf to deposit events into your AWS account.
  * @param creds Credentials to your AWS account if you prefer ngrok to sign in with long-term access keys.
  */
final case class AwsAuth(
  role: Option[AwsRole] = None,
  creds: Option[AwsCredentials] = None
)

object AwsAuth {
  implicit val encodeAwsAuth: io.circe.Encoder[AwsAuth] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.role.map(_.asJson).map(("role", _)),
      value.creds.map(_.asJson).map(("creds", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeAwsAuth: io.circe.Decoder[AwsAuth] = (c: io.circe.HCursor) =>
    for {
      role  <- c.downField("role").as[Option[AwsRole]]
      creds <- c.downField("creds").as[Option[AwsCredentials]]
    } yield AwsAuth(
      role,
      creds
    )
}
