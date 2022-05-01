package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedAddrList]] resource.
  *
  * @constructor create a new ReservedAddrList.
  * @param reservedAddrs the list of all reserved addresses on this account
  * @param uri URI of the reserved address list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class ReservedAddrList(
  reservedAddrs: List[ReservedAddr],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object ReservedAddrList {
  implicit val encodeReservedAddrList: io.circe.Encoder[ReservedAddrList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("reserved_addrs", value.reservedAddrs.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedAddrList: io.circe.Decoder[ReservedAddrList] = (c: io.circe.HCursor) =>
    for {
      reservedAddrs <- c.downField("reserved_addrs").as[Option[List[ReservedAddr]]]
      uri           <- c.downField("uri").as[java.net.URI]
      nextPageUri   <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield ReservedAddrList(
      reservedAddrs.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
