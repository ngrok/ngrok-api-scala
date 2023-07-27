/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[ApplicationUserList]] resource.
 *
 * @constructor create a new ApplicationUserList.
 * @param applicationUsers list of all application users on this account
 * @param uri URI of the application user list API resource
 * @param nextPageUri URI of the next page, or null if there is no next page
 */
final case class ApplicationUserList(
  applicationUsers: List[ApplicationUser],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None,
) extends Pageable

object ApplicationUserList {
  implicit val encodeApplicationUserList: io.circe.Encoder[ApplicationUserList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("application_users", value.applicationUsers.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApplicationUserList: io.circe.Decoder[ApplicationUserList] = (c: io.circe.HCursor) => for {
    applicationUsers <- c.downField("application_users").as[Option[List[ApplicationUser]]]
    uri <- c.downField("uri").as[java.net.URI]
    nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
  } yield ApplicationUserList(
    applicationUsers.getOrElse(List.empty),
    uri,
    nextPageUri,
  )
}
