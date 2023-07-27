/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[IpPolicyRuleList]] resource.
  *
  * @constructor create a new IpPolicyRuleList.
  * @param ipPolicyRules the list of all IP policy rules on this account
  * @param uri URI of the IP policy rule list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class IpPolicyRuleList(
  ipPolicyRules: List[IpPolicyRule],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object IpPolicyRuleList {
  implicit val encodeIpPolicyRuleList: io.circe.Encoder[IpPolicyRuleList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ip_policy_rules", value.ipPolicyRules.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeIpPolicyRuleList: io.circe.Decoder[IpPolicyRuleList] = (c: io.circe.HCursor) =>
    for {
      ipPolicyRules <- c.downField("ip_policy_rules").as[Option[List[IpPolicyRule]]]
      uri           <- c.downField("uri").as[java.net.URI]
      nextPageUri   <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield IpPolicyRuleList(
      ipPolicyRules.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
