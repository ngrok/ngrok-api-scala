/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventSourceReplace]] resource.
  *
  * @constructor create a new EventSourceReplace.
  * @param `type` Type of event for which an event subscription will trigger
  */
final case class EventSourceReplace(
  `type`: String
)

object EventSourceReplace {
  implicit val encodeEventSourceReplace: io.circe.Encoder[EventSourceReplace] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("type", value.`type`.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventSourceReplace: io.circe.Decoder[EventSourceReplace] = (c: io.circe.HCursor) =>
    for {
      `type` <- c.downField("type").as[String]
    } yield EventSourceReplace(
      `type`
    )
}
