package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TlsEdgeList]] resource.
  *
  * @constructor create a new TlsEdgeList.
  * @param tlsEdges the list of all TLS Edges on this account
  * @param uri URI of the TLS Edge list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TlsEdgeList(
  tlsEdges: List[TlsEdge],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object TlsEdgeList {
  implicit val encodeTlsEdgeList: io.circe.Encoder[TlsEdgeList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("tls_edges", value.tlsEdges.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeTlsEdgeList: io.circe.Decoder[TlsEdgeList] = (c: io.circe.HCursor) =>
    for {
      tlsEdges    <- c.downField("tls_edges").as[List[TlsEdge]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TlsEdgeList(
      tlsEdges,
      uri,
      nextPageUri
    )
}
