package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpRestrictionList]] resource.
  *
  * @constructor create a new IpRestrictionList.
  * @param ipRestrictions the list of all IP restrictions on this account
  * @param uri URI of the IP resrtrictions list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class IpRestrictionList(
  ipRestrictions: List[IpRestriction],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object IpRestrictionList {
  implicit val encodeIpRestrictionList: io.circe.Encoder[IpRestrictionList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ip_restrictions", value.ipRestrictions.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeIpRestrictionList: io.circe.Decoder[IpRestrictionList] = (c: io.circe.HCursor) =>
    for {
      ipRestrictions <- c.downField("ip_restrictions").as[List[IpRestriction]]
      uri            <- c.downField("uri").as[java.net.URI]
      nextPageUri    <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield IpRestrictionList(
      ipRestrictions,
      uri,
      nextPageUri
    )
}
