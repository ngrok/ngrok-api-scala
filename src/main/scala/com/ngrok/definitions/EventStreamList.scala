package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventStreamList]] resource.
  *
  * @constructor create a new EventStreamList.
  * @param eventStreams The list of all Event Streams on this account.
  * @param uri URI of the Event Stream list API resource.
  * @param nextPageUri URI of the next page, or null if there is no next page.
  */
final case class EventStreamList(
  eventStreams: List[EventStream],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object EventStreamList {
  implicit val encodeEventStreamList: io.circe.Encoder[EventStreamList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("event_streams", value.eventStreams.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventStreamList: io.circe.Decoder[EventStreamList] = (c: io.circe.HCursor) =>
    for {
      eventStreams <- c.downField("event_streams").as[List[EventStream]]
      uri          <- c.downField("uri").as[java.net.URI]
      nextPageUri  <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield EventStreamList(
      eventStreams,
      uri,
      nextPageUri
    )
}
