package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventSource]] resource.
  *
  * @constructor create a new EventSource.
  * @param `type` Type of event for which an event subscription will trigger
  * @param uri URI of the Event Source API resource.
  */
final case class EventSource(
  `type`: String,
  uri: java.net.URI
)

object EventSource {
  implicit val encodeEventSource: io.circe.Encoder[EventSource] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("type", value.`type`.asJson)),
      Option(("uri", value.uri.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEventSource: io.circe.Decoder[EventSource] = (c: io.circe.HCursor) =>
    for {
      `type` <- c.downField("type").as[String]
      uri    <- c.downField("uri").as[java.net.URI]
    } yield EventSource(
      `type`,
      uri
    )
}
