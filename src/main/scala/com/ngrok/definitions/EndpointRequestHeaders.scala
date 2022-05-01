package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointRequestHeaders]] resource.
  *
  * @constructor create a new EndpointRequestHeaders.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param add a map of header key to header value that will be injected into the HTTP Request before being sent to the upstream application server
  * @param remove a list of header names that will be removed from the HTTP Request before being sent to the upstream application server
  */
final case class EndpointRequestHeaders(
  enabled: Option[Boolean] = None,
  add: Map[String, String],
  remove: List[String]
)

object EndpointRequestHeaders {
  implicit val encodeEndpointRequestHeaders: io.circe.Encoder[EndpointRequestHeaders] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("add", value.add.asJson)),
        Option(("remove", value.remove.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointRequestHeaders: io.circe.Decoder[EndpointRequestHeaders] = (c: io.circe.HCursor) =>
    for {
      enabled <- c.downField("enabled").as[Option[Boolean]]
      add     <- c.downField("add").as[Option[Map[String, String]]]
      remove  <- c.downField("remove").as[Option[List[String]]]
    } yield EndpointRequestHeaders(
      enabled,
      add.getOrElse(Map.empty),
      remove.getOrElse(List.empty)
    )
}
