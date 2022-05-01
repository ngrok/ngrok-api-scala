package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[AgentIngressList]] resource.
  *
  * @constructor create a new AgentIngressList.
  * @param ingresses the list of Agent Ingresses owned by this account
  * @param uri URI of the Agent Ingress list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class AgentIngressList(
  ingresses: List[AgentIngress],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object AgentIngressList {
  implicit val encodeAgentIngressList: io.circe.Encoder[AgentIngressList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ingresses", value.ingresses.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeAgentIngressList: io.circe.Decoder[AgentIngressList] = (c: io.circe.HCursor) =>
    for {
      ingresses   <- c.downField("ingresses").as[Option[List[AgentIngress]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield AgentIngressList(
      ingresses.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
