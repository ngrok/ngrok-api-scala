package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TunnelGroupBackendList]] resource.
  *
  * @constructor create a new TunnelGroupBackendList.
  * @param backends the list of all TunnelGroup backends on this account
  * @param uri URI of the TunnelGroup backends list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TunnelGroupBackendList(
  backends: List[TunnelGroupBackend],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object TunnelGroupBackendList {
  implicit val encodeTunnelGroupBackendList: io.circe.Encoder[TunnelGroupBackendList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("backends", value.backends.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTunnelGroupBackendList: io.circe.Decoder[TunnelGroupBackendList] = (c: io.circe.HCursor) =>
    for {
      backends    <- c.downField("backends").as[List[TunnelGroupBackend]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TunnelGroupBackendList(
      backends,
      uri,
      nextPageUri
    )
}
