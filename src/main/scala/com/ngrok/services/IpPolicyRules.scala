package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object IpPolicyRules {
  private case class IpPolicyRulesCreate(
    description: Option[String],
    metadata: Option[String],
    cidr: String,
    ipPolicyId: String,
    action: Option[String]
  )

  private object IpPolicyRulesCreate {
    implicit val encodeIpPolicyRulesCreate: Encoder[IpPolicyRulesCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        Option(("cidr", value.cidr.asJson)),
        Option(("ip_policy_id", value.ipPolicyId.asJson)),
        value.action.map(_.asJson).map(("action", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class IpPolicyRulesUpdate(
    description: Option[String],
    metadata: Option[String],
    cidr: Option[String]
  )

  private object IpPolicyRulesUpdate {
    implicit val encodeIpPolicyRulesUpdate: Encoder[IpPolicyRulesUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.cidr.map(_.asJson).map(("cidr", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** IP Policy Rules are the IPv4 or IPv6 CIDRs entries that
  *  make up an IP Policy.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules">https://ngrok.com/docs/api#api-ip-policy-rules</a>.
  */
class IpPolicyRules private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import IpPolicyRules._

  /** Create a new IP policy rule attached to an IP Policy.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules-create">https://ngrok.com/docs/api#api-ip-policy-rules-create</a>.
    *
    * @param cidr an IP or IP range specified in CIDR notation. IPv4 and IPv6 are both supported.
    * @param ipPolicyId ID of the IP policy this IP policy rule will be attached to
    * @param description human-readable description of the source IPs of this IP rule. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP policy rule. optional, max 4096 bytes.
    * @param action the action to apply to the policy rule, either <code>allow</code> or <code>deny</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    cidr: String,
    ipPolicyId: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    action: Option[String] = None
  ): Future[IpPolicyRule] =
    apiClient.sendRequest[IpPolicyRule](
      NgrokApiClient.HttpMethod.Post,
      "/ip_policy_rules",
      List.empty,
      Option(
        IpPolicyRulesCreate(
          description,
          metadata,
          cidr,
          ipPolicyId,
          action
        ).asJson
      )
    )

  /** Delete an IP policy rule.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules-delete">https://ngrok.com/docs/api#api-ip-policy-rules-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ip_policy_rules/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an IP policy rule by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules-get">https://ngrok.com/docs/api#api-ip-policy-rules-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[IpPolicyRule] =
    apiClient.sendRequest[IpPolicyRule](
      NgrokApiClient.HttpMethod.Get,
      s"/ip_policy_rules/$id",
      List.empty,
      Option.empty
    )

  /** List all IP policy rules on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules-list">https://ngrok.com/docs/api#api-ip-policy-rules-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[IpPolicyRuleList]] =
    apiClient
      .sendRequest[IpPolicyRuleList](
        NgrokApiClient.HttpMethod.Get,
        "/ip_policy_rules",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an IP policy rule by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policy-rules-update">https://ngrok.com/docs/api#api-ip-policy-rules-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of the source IPs of this IP rule. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP policy rule. optional, max 4096 bytes.
    * @param cidr an IP or IP range specified in CIDR notation. IPv4 and IPv6 are both supported.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    cidr: Option[String] = None
  ): Future[IpPolicyRule] =
    apiClient.sendRequest[IpPolicyRule](
      NgrokApiClient.HttpMethod.Patch,
      s"/ip_policy_rules/$id",
      List.empty,
      Option(
        IpPolicyRulesUpdate(
          description,
          metadata,
          cidr
        ).asJson
      )
    )

}
