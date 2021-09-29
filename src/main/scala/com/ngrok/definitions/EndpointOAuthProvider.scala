package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOAuthProvider]] resource.
  *
  * @constructor create a new EndpointOAuthProvider.
  * @param github configuration for using github as the identity provider
  * @param facebook configuration for using facebook as the identity provider
  * @param microsoft configuration for using microsoft as the identity provider
  * @param google configuration for using google as the identity provider
  */
final case class EndpointOAuthProvider(
  github: Option[EndpointOAuthGitHub],
  facebook: Option[EndpointOAuthFacebook],
  microsoft: Option[EndpointOAuthMicrosoft],
  google: Option[EndpointOAuthGoogle]
)

object EndpointOAuthProvider {
  implicit val encodeEndpointOAuthProvider: io.circe.Encoder[EndpointOAuthProvider] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.github.map(_.asJson).map(("github", _)),
        value.facebook.map(_.asJson).map(("facebook", _)),
        value.microsoft.map(_.asJson).map(("microsoft", _)),
        value.google.map(_.asJson).map(("google", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointOAuthProvider: io.circe.Decoder[EndpointOAuthProvider] = (c: io.circe.HCursor) =>
    for {
      github    <- c.downField("github").as[Option[EndpointOAuthGitHub]]
      facebook  <- c.downField("facebook").as[Option[EndpointOAuthFacebook]]
      microsoft <- c.downField("microsoft").as[Option[EndpointOAuthMicrosoft]]
      google    <- c.downField("google").as[Option[EndpointOAuthGoogle]]
    } yield EndpointOAuthProvider(
      github,
      facebook,
      microsoft,
      google
    )
}
