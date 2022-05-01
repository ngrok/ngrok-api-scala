package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object IpRestrictions {
  private case class IpRestrictionsCreate(
    description: Option[String],
    metadata: Option[String],
    enforced: Option[Boolean],
    `type`: String,
    ipPolicyIds: List[String]
  )

  private object IpRestrictionsCreate {
    implicit val encodeIpRestrictionsCreate: Encoder[IpRestrictionsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.enforced.map(_.asJson).map(("enforced", _)),
        Option(("type", value.`type`.asJson)),
        Option(("ip_policy_ids", value.ipPolicyIds.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class IpRestrictionsUpdate(
    description: Option[String],
    metadata: Option[String],
    enforced: Option[Boolean],
    ipPolicyIds: List[String]
  )

  private object IpRestrictionsUpdate {
    implicit val encodeIpRestrictionsUpdate: Encoder[IpRestrictionsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.enforced.map(_.asJson).map(("enforced", _)),
        if (value.ipPolicyIds.isEmpty) None else Option(("ip_policy_ids", value.ipPolicyIds.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An IP restriction is a restriction placed on the CIDRs that are allowed to
  *  initiate traffic to a specific aspect of your ngrok account. An IP
  *  restriction has a type which defines the ingress it applies to. IP
  *  restrictions can be used to enforce the source IPs that can make API
  *  requests, log in to the dashboard, start ngrok agents, and connect to your
  *  public-facing endpoints.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions">https://ngrok.com/docs/api#api-ip-restrictions</a>.
  */
class IpRestrictions private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import IpRestrictions._

  /** Create a new IP restriction
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions-create">https://ngrok.com/docs/api#api-ip-restrictions-create</a>.
    *
    * @param `type` the type of IP restriction. this defines what traffic will be restricted with the attached policies. four values are currently supported: <code>dashboard</code>, <code>api</code>, <code>agent</code>, and <code>endpoints</code>
    * @param ipPolicyIds the set of IP policy identifiers that are used to enforce the restriction
    * @param description human-readable description of this IP restriction. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP restriction. optional, max 4096 bytes.
    * @param enforced true if the IP restriction will be enforced. if false, only warnings will be issued
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    `type`: String,
    ipPolicyIds: List[String],
    description: Option[String] = None,
    metadata: Option[String] = None,
    enforced: Option[Boolean] = None
  ): Future[IpRestriction] =
    apiClient.sendRequest[IpRestriction](
      NgrokApiClient.HttpMethod.Post,
      "/ip_restrictions",
      List.empty,
      Option(
        IpRestrictionsCreate(
          description,
          metadata,
          enforced,
          `type`,
          ipPolicyIds
        ).asJson
      )
    )

  /** Delete an IP restriction
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions-delete">https://ngrok.com/docs/api#api-ip-restrictions-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ip_restrictions/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an IP restriction
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions-get">https://ngrok.com/docs/api#api-ip-restrictions-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[IpRestriction] =
    apiClient.sendRequest[IpRestriction](
      NgrokApiClient.HttpMethod.Get,
      s"/ip_restrictions/$id",
      List.empty,
      Option.empty
    )

  /** List all IP restrictions on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions-list">https://ngrok.com/docs/api#api-ip-restrictions-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[IpRestrictionList]] =
    apiClient
      .sendRequest[IpRestrictionList](
        NgrokApiClient.HttpMethod.Get,
        "/ip_restrictions",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an IP restriction by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-restrictions-update">https://ngrok.com/docs/api#api-ip-restrictions-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this IP restriction. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP restriction. optional, max 4096 bytes.
    * @param enforced true if the IP restriction will be enforced. if false, only warnings will be issued
    * @param ipPolicyIds the set of IP policy identifiers that are used to enforce the restriction
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    enforced: Option[Boolean] = None,
    ipPolicyIds: List[String] = List.empty
  ): Future[IpRestriction] =
    apiClient.sendRequest[IpRestriction](
      NgrokApiClient.HttpMethod.Patch,
      s"/ip_restrictions/$id",
      List.empty,
      Option(
        IpRestrictionsUpdate(
          description,
          metadata,
          enforced,
          ipPolicyIds
        ).asJson
      )
    )

}
