package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpWhitelistEntryList]] resource.
  *
  * @constructor create a new IpWhitelistEntryList.
  * @param whitelist the list of all IP whitelist entries on this account
  * @param uri URI of the IP whitelist API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class IpWhitelistEntryList(
  whitelist: List[IpWhitelistEntry],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object IpWhitelistEntryList {
  implicit val encodeIpWhitelistEntryList: io.circe.Encoder[IpWhitelistEntryList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("whitelist", value.whitelist.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeIpWhitelistEntryList: io.circe.Decoder[IpWhitelistEntryList] = (c: io.circe.HCursor) =>
    for {
      whitelist   <- c.downField("whitelist").as[List[IpWhitelistEntry]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield IpWhitelistEntryList(
      whitelist,
      uri,
      nextPageUri
    )
}
