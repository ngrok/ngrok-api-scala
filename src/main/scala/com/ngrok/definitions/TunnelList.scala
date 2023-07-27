/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TunnelList]] resource.
  *
  * @constructor create a new TunnelList.
  * @param tunnels the list of all online tunnels on this account
  * @param uri URI of the tunnels list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TunnelList(
  tunnels: List[Tunnel],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object TunnelList {
  implicit val encodeTunnelList: io.circe.Encoder[TunnelList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("tunnels", value.tunnels.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeTunnelList: io.circe.Decoder[TunnelList] = (c: io.circe.HCursor) =>
    for {
      tunnels     <- c.downField("tunnels").as[Option[List[Tunnel]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TunnelList(
      tunnels.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
