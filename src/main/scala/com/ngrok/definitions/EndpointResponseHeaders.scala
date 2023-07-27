/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointResponseHeaders]] resource.
  *
  * @constructor create a new EndpointResponseHeaders.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param add a map of header key to header value that will be injected into the HTTP Response returned to the HTTP client
  * @param remove a list of header names that will be removed from the HTTP Response returned to the HTTP client
  */
final case class EndpointResponseHeaders(
  enabled: Option[Boolean] = None,
  add: Map[String, String],
  remove: List[String]
)

object EndpointResponseHeaders {
  implicit val encodeEndpointResponseHeaders: io.circe.Encoder[EndpointResponseHeaders] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("add", value.add.asJson)),
        Option(("remove", value.remove.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointResponseHeaders: io.circe.Decoder[EndpointResponseHeaders] = (c: io.circe.HCursor) =>
    for {
      enabled <- c.downField("enabled").as[Option[Boolean]]
      add     <- c.downField("add").as[Option[Map[String, String]]]
      remove  <- c.downField("remove").as[Option[List[String]]]
    } yield EndpointResponseHeaders(
      enabled,
      add.getOrElse(Map.empty),
      remove.getOrElse(List.empty)
    )
}
