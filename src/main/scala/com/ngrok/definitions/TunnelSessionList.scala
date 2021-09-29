package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TunnelSessionList]] resource.
  *
  * @constructor create a new TunnelSessionList.
  * @param tunnelSessions list of all tunnel sessions on this account
  * @param uri URI to the API resource of the tunnel session list
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TunnelSessionList(
  tunnelSessions: List[TunnelSession],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI]
) extends Pageable

object TunnelSessionList {
  implicit val encodeTunnelSessionList: io.circe.Encoder[TunnelSessionList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("tunnel_sessions", value.tunnelSessions.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTunnelSessionList: io.circe.Decoder[TunnelSessionList] = (c: io.circe.HCursor) =>
    for {
      tunnelSessions <- c.downField("tunnel_sessions").as[List[TunnelSession]]
      uri            <- c.downField("uri").as[java.net.URI]
      nextPageUri    <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TunnelSessionList(
      tunnelSessions,
      uri,
      nextPageUri
    )
}
