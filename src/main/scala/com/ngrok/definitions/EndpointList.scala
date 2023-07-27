/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointList]] resource.
  *
  * @constructor create a new EndpointList.
  * @param endpoints the list of all active endpoints on this account
  * @param uri URI of the endpoints list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class EndpointList(
  endpoints: List[Endpoint],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object EndpointList {
  implicit val encodeEndpointList: io.circe.Encoder[EndpointList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("endpoints", value.endpoints.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeEndpointList: io.circe.Decoder[EndpointList] = (c: io.circe.HCursor) =>
    for {
      endpoints   <- c.downField("endpoints").as[Option[List[Endpoint]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield EndpointList(
      endpoints.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
