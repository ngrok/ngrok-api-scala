/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointOAuthProvider]] resource.
  *
  * @constructor create a new EndpointOAuthProvider.
  * @param github configuration for using github as the identity provider
  * @param facebook configuration for using facebook as the identity provider
  * @param microsoft configuration for using microsoft as the identity provider
  * @param google configuration for using google as the identity provider
  * @param linkedin configuration for using linkedin as the identity provider
  * @param gitlab configuration for using gitlab as the identity provider
  * @param twitch configuration for using twitch as the identity provider
  * @param amazon configuration for using amazon as the identity provider
  */
final case class EndpointOAuthProvider(
  github: Option[EndpointOAuthGitHub] = None,
  facebook: Option[EndpointOAuthFacebook] = None,
  microsoft: Option[EndpointOAuthMicrosoft] = None,
  google: Option[EndpointOAuthGoogle] = None,
  linkedin: Option[EndpointOAuthLinkedIn] = None,
  gitlab: Option[EndpointOAuthGitLab] = None,
  twitch: Option[EndpointOAuthTwitch] = None,
  amazon: Option[EndpointOAuthAmazon] = None
)

object EndpointOAuthProvider {
  implicit val encodeEndpointOAuthProvider: io.circe.Encoder[EndpointOAuthProvider] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.github.map(_.asJson).map(("github", _)),
        value.facebook.map(_.asJson).map(("facebook", _)),
        value.microsoft.map(_.asJson).map(("microsoft", _)),
        value.google.map(_.asJson).map(("google", _)),
        value.linkedin.map(_.asJson).map(("linkedin", _)),
        value.gitlab.map(_.asJson).map(("gitlab", _)),
        value.twitch.map(_.asJson).map(("twitch", _)),
        value.amazon.map(_.asJson).map(("amazon", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointOAuthProvider: io.circe.Decoder[EndpointOAuthProvider] = (c: io.circe.HCursor) =>
    for {
      github    <- c.downField("github").as[Option[EndpointOAuthGitHub]]
      facebook  <- c.downField("facebook").as[Option[EndpointOAuthFacebook]]
      microsoft <- c.downField("microsoft").as[Option[EndpointOAuthMicrosoft]]
      google    <- c.downField("google").as[Option[EndpointOAuthGoogle]]
      linkedin  <- c.downField("linkedin").as[Option[EndpointOAuthLinkedIn]]
      gitlab    <- c.downField("gitlab").as[Option[EndpointOAuthGitLab]]
      twitch    <- c.downField("twitch").as[Option[EndpointOAuthTwitch]]
      amazon    <- c.downField("amazon").as[Option[EndpointOAuthAmazon]]
    } yield EndpointOAuthProvider(
      github,
      facebook,
      microsoft,
      google,
      linkedin,
      gitlab,
      twitch,
      amazon
    )
}
