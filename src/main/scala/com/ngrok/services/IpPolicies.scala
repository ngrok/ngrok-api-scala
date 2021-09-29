package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object IpPolicies {
  private case class IpPoliciesCreate(
    description: Option[String],
    metadata: Option[String],
    action: String
  )

  private object IpPoliciesCreate {
    implicit val encodeIpPoliciesCreate: Encoder[IpPoliciesCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        Option(("action", value.action.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class IpPoliciesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object IpPoliciesUpdate {
    implicit val encodeIpPoliciesUpdate: Encoder[IpPoliciesUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** IP Policies are reusable groups of CIDR ranges with an <code>allow</code> or
  * <code>deny</code>
  *  action. They can be attached to endpoints via the Endpoint Configuration IP
  *  Policy module. They can also be used with IP Restrictions to control source
  *  IP ranges that can start tunnel sessions and connect to the API and dashboard.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ip-policies">https://ngrok.com/docs/api#api-ip-policies</a>.
  */
class IpPolicies private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import IpPolicies._

  /** Create a new IP policy. It will not apply to any traffic until you associate to
    * a traffic source via an endpoint configuration or IP restriction.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies-create">https://ngrok.com/docs/api#api-ip-policies-create</a>.
    *
    * @param action the IP policy action. Supported values are <code>allow</code> or <code>deny</code>
    * @param description human-readable description of the source IPs of this IP policy. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP policy. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    action: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[IpPolicy] =
    apiClient.sendRequest[IpPolicy](
      NgrokApiClient.HttpMethod.Post,
      "/ip_policies",
      List.empty,
      Option(
        IpPoliciesCreate(
          description,
          metadata,
          action
        ).asJson
      )
    )

  /** Delete an IP policy. If the IP policy is referenced by another object for the
    * purposes of traffic restriction it will be treated as if the IP policy remains
    * but has zero rules.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies-delete">https://ngrok.com/docs/api#api-ip-policies-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ip_policies/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an IP policy by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies-get">https://ngrok.com/docs/api#api-ip-policies-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[IpPolicy] =
    apiClient.sendRequest[IpPolicy](
      NgrokApiClient.HttpMethod.Get,
      s"/ip_policies/$id",
      List.empty,
      Option.empty
    )

  /** List all IP policies on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies-list">https://ngrok.com/docs/api#api-ip-policies-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[IpPolicyList]] =
    apiClient
      .sendRequest[IpPolicyList](
        NgrokApiClient.HttpMethod.Get,
        "/ip_policies",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an IP policy by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-policies-update">https://ngrok.com/docs/api#api-ip-policies-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of the source IPs of this IP policy. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP policy. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[IpPolicy] =
    apiClient.sendRequest[IpPolicy](
      NgrokApiClient.HttpMethod.Patch,
      s"/ip_policies/$id",
      List.empty,
      Option(
        IpPoliciesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
