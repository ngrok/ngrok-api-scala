package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpPolicyList]] resource.
  *
  * @constructor create a new IpPolicyList.
  * @param ipPolicies the list of all IP policies on this account
  * @param uri URI of the IP policy list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class IpPolicyList(
  ipPolicies: List[IpPolicy],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object IpPolicyList {
  implicit val encodeIpPolicyList: io.circe.Encoder[IpPolicyList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("ip_policies", value.ipPolicies.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeIpPolicyList: io.circe.Decoder[IpPolicyList] = (c: io.circe.HCursor) =>
    for {
      ipPolicies  <- c.downField("ip_policies").as[Option[List[IpPolicy]]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield IpPolicyList(
      ipPolicies.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
