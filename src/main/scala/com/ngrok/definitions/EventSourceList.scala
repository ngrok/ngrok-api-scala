package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventSourceList]] resource.
  *
  * @constructor create a new EventSourceList.
  * @param sources The list of all Event Sources for an Event Subscription
  * @param uri URI of the next page, or null if there is no next page.
  */
final case class EventSourceList(
  sources: List[EventSource],
  uri: java.net.URI
)

object EventSourceList {
  implicit val encodeEventSourceList: io.circe.Encoder[EventSourceList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("sources", value.sources.asJson)),
        Option(("uri", value.uri.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventSourceList: io.circe.Decoder[EventSourceList] = (c: io.circe.HCursor) =>
    for {
      sources <- c.downField("sources").as[List[EventSource]]
      uri     <- c.downField("uri").as[java.net.URI]
    } yield EventSourceList(
      sources,
      uri
    )
}
