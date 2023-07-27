/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ReservedDomainList]] resource.
  *
  * @constructor create a new ReservedDomainList.
  * @param reservedDomains the list of all reserved domains on this account
  * @param uri URI of the reserved domain list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class ReservedDomainList(
  reservedDomains: List[ReservedDomain],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object ReservedDomainList {
  implicit val encodeReservedDomainList: io.circe.Encoder[ReservedDomainList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("reserved_domains", value.reservedDomains.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeReservedDomainList: io.circe.Decoder[ReservedDomainList] = (c: io.circe.HCursor) =>
    for {
      reservedDomains <- c.downField("reserved_domains").as[Option[List[ReservedDomain]]]
      uri             <- c.downField("uri").as[java.net.URI]
      nextPageUri     <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield ReservedDomainList(
      reservedDomains.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
