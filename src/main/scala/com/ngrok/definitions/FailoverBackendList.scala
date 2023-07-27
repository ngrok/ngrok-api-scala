/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[FailoverBackendList]] resource.
  *
  * @constructor create a new FailoverBackendList.
  * @param backends the list of all Failover backends on this account
  * @param uri URI of the Failover backends list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class FailoverBackendList(
  backends: List[FailoverBackend],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object FailoverBackendList {
  implicit val encodeFailoverBackendList: io.circe.Encoder[FailoverBackendList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("backends", value.backends.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeFailoverBackendList: io.circe.Decoder[FailoverBackendList] = (c: io.circe.HCursor) =>
    for {
      backends    <- c.downField("backends").as[Option[List[FailoverBackend]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield FailoverBackendList(
      backends.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
