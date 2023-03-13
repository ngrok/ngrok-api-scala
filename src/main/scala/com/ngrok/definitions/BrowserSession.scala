package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[BrowserSession]] resource.
 *
 * @constructor create a new BrowserSession.
 * @param userAgent HTTP User-Agent data
 * @param ipAddress IP address
 * @param location IP geolocation data
 */
final case class BrowserSession(
  userAgent: UserAgent,
  ipAddress: String,
  location: Option[Location] = None,
)

object BrowserSession {
  implicit val encodeBrowserSession: io.circe.Encoder[BrowserSession] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("user_agent", value.userAgent.asJson)),
      Option(("ip_address", value.ipAddress.asJson)),
      value.location.map(_.asJson).map(("location", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeBrowserSession: io.circe.Decoder[BrowserSession] = (c: io.circe.HCursor) => for {
    userAgent <- c.downField("user_agent").as[UserAgent]
    ipAddress <- c.downField("ip_address").as[String]
    location <- c.downField("location").as[Option[Location]]
  } yield BrowserSession(
    userAgent,
    ipAddress,
    location,
  )
}
