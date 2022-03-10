package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[HttpResponseBackendList]] resource.
  *
  * @constructor create a new HttpResponseBackendList.
  * @param backends the value of the <code>backends</code> parameter as a [[scala.List]] of [[HttpResponseBackend]]
  * @param uri the value of the <code>uri</code> parameter as a [[java.net.URI]]
  * @param nextPageUri the value of the <code>next_page_uri</code> parameter as a [[java.net.URI]]
  */
final case class HttpResponseBackendList(
  backends: List[HttpResponseBackend],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object HttpResponseBackendList {
  implicit val encodeHttpResponseBackendList: io.circe.Encoder[HttpResponseBackendList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("backends", value.backends.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeHttpResponseBackendList: io.circe.Decoder[HttpResponseBackendList] = (c: io.circe.HCursor) =>
    for {
      backends    <- c.downField("backends").as[List[HttpResponseBackend]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield HttpResponseBackendList(
      backends,
      uri,
      nextPageUri
    )
}
