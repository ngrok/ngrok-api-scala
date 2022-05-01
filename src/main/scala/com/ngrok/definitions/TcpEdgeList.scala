package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TcpEdgeList]] resource.
  *
  * @constructor create a new TcpEdgeList.
  * @param tcpEdges the list of all TCP Edges on this account
  * @param uri URI of the TCP Edge list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TcpEdgeList(
  tcpEdges: List[TcpEdge],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object TcpEdgeList {
  implicit val encodeTcpEdgeList: io.circe.Encoder[TcpEdgeList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("tcp_edges", value.tcpEdges.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeTcpEdgeList: io.circe.Decoder[TcpEdgeList] = (c: io.circe.HCursor) =>
    for {
      tcpEdges    <- c.downField("tcp_edges").as[Option[List[TcpEdge]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TcpEdgeList(
      tcpEdges.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
