package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[IdentityProvider]] resource.
 *
 * @constructor create a new IdentityProvider.
 * @param name name of the identity provider (e.g. Google)
 * @param url URL of the identity provider (e.g. <a href="https://accounts.google.com">https://accounts.google.com</a>)
 */
final case class IdentityProvider(
  name: String,
  url: String,
)

object IdentityProvider {
  implicit val encodeIdentityProvider: io.circe.Encoder[IdentityProvider] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("name", value.name.asJson)),
      Option(("url", value.url.asJson)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeIdentityProvider: io.circe.Decoder[IdentityProvider] = (c: io.circe.HCursor) => for {
    name <- c.downField("name").as[String]
    url <- c.downField("url").as[String]
  } yield IdentityProvider(
    name,
    url,
  )
}
