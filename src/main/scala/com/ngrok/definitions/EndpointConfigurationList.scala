package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointConfigurationList]] resource.
  *
  * @constructor create a new EndpointConfigurationList.
  * @param endpointConfigurations the list of all endpoint configurations on this account
  * @param uri URI of the endpoint configurations list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class EndpointConfigurationList(
  endpointConfigurations: List[EndpointConfiguration],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object EndpointConfigurationList {
  implicit val encodeEndpointConfigurationList: io.circe.Encoder[EndpointConfigurationList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("endpoint_configurations", value.endpointConfigurations.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointConfigurationList: io.circe.Decoder[EndpointConfigurationList] = (c: io.circe.HCursor) =>
    for {
      endpointConfigurations <- c.downField("endpoint_configurations").as[List[EndpointConfiguration]]
      uri                    <- c.downField("uri").as[java.net.URI]
      nextPageUri            <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield EndpointConfigurationList(
      endpointConfigurations,
      uri,
      nextPageUri
    )
}
