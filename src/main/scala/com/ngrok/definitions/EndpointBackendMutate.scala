/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointBackendMutate]] resource.
  *
  * @constructor create a new EndpointBackendMutate.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param backendId backend to be used to back this endpoint
  */
final case class EndpointBackendMutate(
  enabled: Option[Boolean] = None,
  backendId: String
)

object EndpointBackendMutate {
  implicit val encodeEndpointBackendMutate: io.circe.Encoder[EndpointBackendMutate] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("backend_id", value.backendId.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointBackendMutate: io.circe.Decoder[EndpointBackendMutate] = (c: io.circe.HCursor) =>
    for {
      enabled   <- c.downField("enabled").as[Option[Boolean]]
      backendId <- c.downField("backend_id").as[String]
    } yield EndpointBackendMutate(
      enabled,
      backendId
    )
}
