package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EventSubscriptionList]] resource.
  *
  * @constructor create a new EventSubscriptionList.
  * @param eventSubscriptions The list of all Event Subscriptions on this account.
  * @param uri URI of the Event Subscriptions list API resource.
  * @param nextPageUri URI of next page, or null if there is no next page.
  */
final case class EventSubscriptionList(
  eventSubscriptions: List[EventSubscription],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI]
) extends Pageable

object EventSubscriptionList {
  implicit val encodeEventSubscriptionList: io.circe.Encoder[EventSubscriptionList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("event_subscriptions", value.eventSubscriptions.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEventSubscriptionList: io.circe.Decoder[EventSubscriptionList] = (c: io.circe.HCursor) =>
    for {
      eventSubscriptions <- c.downField("event_subscriptions").as[List[EventSubscription]]
      uri                <- c.downField("uri").as[java.net.URI]
      nextPageUri        <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield EventSubscriptionList(
      eventSubscriptions,
      uri,
      nextPageUri
    )
}
