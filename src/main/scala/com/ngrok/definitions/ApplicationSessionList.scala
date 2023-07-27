/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[ApplicationSessionList]] resource.
 *
 * @constructor create a new ApplicationSessionList.
 * @param applicationSessions list of all application sessions on this account
 * @param uri URI of the application session list API resource
 * @param nextPageUri URI of the next page, or null if there is no next page
 */
final case class ApplicationSessionList(
  applicationSessions: List[ApplicationSession],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None,
) extends Pageable

object ApplicationSessionList {
  implicit val encodeApplicationSessionList: io.circe.Encoder[ApplicationSessionList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("application_sessions", value.applicationSessions.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApplicationSessionList: io.circe.Decoder[ApplicationSessionList] = (c: io.circe.HCursor) => for {
    applicationSessions <- c.downField("application_sessions").as[Option[List[ApplicationSession]]]
    uri <- c.downField("uri").as[java.net.URI]
    nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
  } yield ApplicationSessionList(
    applicationSessions.getOrElse(List.empty),
    uri,
    nextPageUri,
  )
}
