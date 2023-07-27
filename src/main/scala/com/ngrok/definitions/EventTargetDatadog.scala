/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[EventTargetDatadog]] resource.
 *
 * @constructor create a new EventTargetDatadog.
 * @param apiKey Datadog API key to use.
 * @param ddtags Tags to send with the event.
 * @param service Service name to send with the event.
 * @param ddsite Datadog site to send event to.
 */
final case class EventTargetDatadog(
  apiKey: Option[String] = None,
  ddtags: Option[String] = None,
  service: Option[String] = None,
  ddsite: Option[String] = None,
)

object EventTargetDatadog {
  implicit val encodeEventTargetDatadog: io.circe.Encoder[EventTargetDatadog] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      value.apiKey.map(_.asJson).map(("api_key", _)),
      value.ddtags.map(_.asJson).map(("ddtags", _)),
      value.service.map(_.asJson).map(("service", _)),
      value.ddsite.map(_.asJson).map(("ddsite", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEventTargetDatadog: io.circe.Decoder[EventTargetDatadog] = (c: io.circe.HCursor) => for {
    apiKey <- c.downField("api_key").as[Option[String]]
    ddtags <- c.downField("ddtags").as[Option[String]]
    service <- c.downField("service").as[Option[String]]
    ddsite <- c.downField("ddsite").as[Option[String]]
  } yield EventTargetDatadog(
    apiKey,
    ddtags,
    service,
    ddsite,
  )
}
