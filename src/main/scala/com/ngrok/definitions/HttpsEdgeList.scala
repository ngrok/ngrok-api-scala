package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[HttpsEdgeList]] resource.
  *
  * @constructor create a new HttpsEdgeList.
  * @param httpsEdges the list of all HTTPS Edges on this account
  * @param uri URI of the HTTPS Edge list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class HttpsEdgeList(
  httpsEdges: List[HttpsEdge],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object HttpsEdgeList {
  implicit val encodeHttpsEdgeList: io.circe.Encoder[HttpsEdgeList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("https_edges", value.httpsEdges.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeHttpsEdgeList: io.circe.Decoder[HttpsEdgeList] = (c: io.circe.HCursor) =>
    for {
      httpsEdges  <- c.downField("https_edges").as[List[HttpsEdge]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield HttpsEdgeList(
      httpsEdges,
      uri,
      nextPageUri
    )
}
