/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[UserAgent]] resource.
 *
 * @constructor create a new UserAgent.
 * @param raw raw User-Agent request header
 * @param browserName browser name (e.g. Chrome)
 * @param browserVersion browser version (e.g. 102)
 * @param deviceType type of device (e.g. Desktop)
 * @param osName operating system name (e.g. MacOS)
 * @param osVersion operating system version (e.g. 10.15.7)
 */
final case class UserAgent(
  raw: String,
  browserName: String,
  browserVersion: String,
  deviceType: String,
  osName: String,
  osVersion: String,
)

object UserAgent {
  implicit val encodeUserAgent: io.circe.Encoder[UserAgent] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("raw", value.raw.asJson)),
      Option(("browser_name", value.browserName.asJson)),
      Option(("browser_version", value.browserVersion.asJson)),
      Option(("device_type", value.deviceType.asJson)),
      Option(("os_name", value.osName.asJson)),
      Option(("os_version", value.osVersion.asJson)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeUserAgent: io.circe.Decoder[UserAgent] = (c: io.circe.HCursor) => for {
    raw <- c.downField("raw").as[String]
    browserName <- c.downField("browser_name").as[String]
    browserVersion <- c.downField("browser_version").as[String]
    deviceType <- c.downField("device_type").as[String]
    osName <- c.downField("os_name").as[String]
    osVersion <- c.downField("os_version").as[String]
  } yield UserAgent(
    raw,
    browserName,
    browserVersion,
    deviceType,
    osName,
    osVersion,
  )
}
