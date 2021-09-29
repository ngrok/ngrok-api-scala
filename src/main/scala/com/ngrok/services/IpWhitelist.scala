package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object IpWhitelist {
  private case class IpWhitelistCreate(
    description: Option[String],
    metadata: Option[String],
    ipNet: Option[String]
  )

  private object IpWhitelistCreate {
    implicit val encodeIpWhitelistCreate: Encoder[IpWhitelistCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.ipNet.map(_.asJson).map(("ip_net", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class IpWhitelistUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object IpWhitelistUpdate {
    implicit val encodeIpWhitelistUpdate: Encoder[IpWhitelistUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** The IP Whitelist is deprecated and will be removed. Use an IP Restriction
  *  with an <code>endpoints</code> type instead.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist">https://ngrok.com/docs/api#api-ip-whitelist</a>.
  */
class IpWhitelist private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import IpWhitelist._

  /** Create a new IP whitelist entry that will restrict traffic to all tunnel
    * endpoints on the account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist-create">https://ngrok.com/docs/api#api-ip-whitelist-create</a>.
    *
    * @param description human-readable description of the source IPs for this IP whitelist entry. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP whitelist entry. optional, max 4096 bytes.
    * @param ipNet an IP address or IP network range in CIDR notation (e.g. 10.1.1.1 or 10.1.0.0/16) of addresses that will be whitelisted to communicate with your tunnel endpoints
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    ipNet: Option[String] = None
  ): Future[IpWhitelistEntry] =
    apiClient.sendRequest[IpWhitelistEntry](
      NgrokApiClient.HttpMethod.Post,
      "/ip_whitelist",
      List.empty,
      Option(
        IpWhitelistCreate(
          description,
          metadata,
          ipNet
        ).asJson
      )
    )

  /** Delete an IP whitelist entry.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist-delete">https://ngrok.com/docs/api#api-ip-whitelist-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ip_whitelist/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an IP whitelist entry by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist-get">https://ngrok.com/docs/api#api-ip-whitelist-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[IpWhitelistEntry] =
    apiClient.sendRequest[IpWhitelistEntry](
      NgrokApiClient.HttpMethod.Get,
      s"/ip_whitelist/$id",
      List.empty,
      Option.empty
    )

  /** List all IP whitelist entries on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist-list">https://ngrok.com/docs/api#api-ip-whitelist-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[IpWhitelistEntryList]] =
    apiClient
      .sendRequest[IpWhitelistEntryList](
        NgrokApiClient.HttpMethod.Get,
        "/ip_whitelist",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an IP whitelist entry by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ip-whitelist-update">https://ngrok.com/docs/api#api-ip-whitelist-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of the source IPs for this IP whitelist entry. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this IP whitelist entry. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[IpWhitelistEntry] =
    apiClient.sendRequest[IpWhitelistEntry](
      NgrokApiClient.HttpMethod.Patch,
      s"/ip_whitelist/$id",
      List.empty,
      Option(
        IpWhitelistUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
