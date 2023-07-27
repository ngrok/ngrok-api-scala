/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[CredentialList]] resource.
  *
  * @constructor create a new CredentialList.
  * @param credentials the list of all tunnel credentials on this account
  * @param uri URI of the tunnel credential list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class CredentialList(
  credentials: List[Credential],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object CredentialList {
  implicit val encodeCredentialList: io.circe.Encoder[CredentialList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("credentials", value.credentials.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeCredentialList: io.circe.Decoder[CredentialList] = (c: io.circe.HCursor) =>
    for {
      credentials <- c.downField("credentials").as[Option[List[Credential]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield CredentialList(
      credentials.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
