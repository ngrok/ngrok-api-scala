package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventDestinationList]] resource.
  *
  * @constructor create a new EventDestinationList.
  * @param eventDestinations The list of all Event Destinations on this account.
  * @param uri URI of the Event Destinations list API resource.
  * @param nextPageUri URI of the next page, or null if there is no next page.
  */
final case class EventDestinationList(
  eventDestinations: List[EventDestination],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object EventDestinationList {
  implicit val encodeEventDestinationList: io.circe.Encoder[EventDestinationList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("event_destinations", value.eventDestinations.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventDestinationList: io.circe.Decoder[EventDestinationList] = (c: io.circe.HCursor) =>
    for {
      eventDestinations <- c.downField("event_destinations").as[List[EventDestination]]
      uri               <- c.downField("uri").as[java.net.URI]
      nextPageUri       <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield EventDestinationList(
      eventDestinations,
      uri,
      nextPageUri
    )
}
