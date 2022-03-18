package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object ReservedAddrs {
  private case class ReservedAddrsCreate(
    description: Option[String],
    metadata: Option[String],
    region: Option[String]
  )

  private object ReservedAddrsCreate {
    implicit val encodeReservedAddrsCreate: Encoder[ReservedAddrsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.region.map(_.asJson).map(("region", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class ReservedAddrsUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object ReservedAddrsUpdate {
    implicit val encodeReservedAddrsUpdate: Encoder[ReservedAddrsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** Reserved Addresses are TCP addresses that can be used to listen for traffic.
  *  TCP address hostnames and ports are assigned by ngrok, they cannot be
  *  chosen.
  *
  * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs">https://ngrok.com/docs/api#api-reserved-addrs</a>.
  */
class ReservedAddrs private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import ReservedAddrs._

  /** Create a new reserved address.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs-create">https://ngrok.com/docs/api#api-reserved-addrs-create</a>.
    *
    * @param description human-readable description of what this reserved address will be used for
    * @param metadata arbitrary user-defined machine-readable data of this reserved address. Optional, max 4096 bytes.
    * @param region reserve the address in this geographic ngrok datacenter. Optional, default is us. (au, eu, ap, us, jp, in, sa)
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    region: Option[String] = None
  ): Future[ReservedAddr] =
    apiClient.sendRequest[ReservedAddr](
      NgrokApiClient.HttpMethod.Post,
      "/reserved_addrs",
      List.empty,
      Option(
        ReservedAddrsCreate(
          description,
          metadata,
          region
        ).asJson
      )
    )

  /** Delete a reserved address.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs-delete">https://ngrok.com/docs/api#api-reserved-addrs-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_addrs/$id",
      List.empty,
      Option.empty
    )

  /** Get the details of a reserved address.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs-get">https://ngrok.com/docs/api#api-reserved-addrs-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[ReservedAddr] =
    apiClient.sendRequest[ReservedAddr](
      NgrokApiClient.HttpMethod.Get,
      s"/reserved_addrs/$id",
      List.empty,
      Option.empty
    )

  /** List all reserved addresses on this account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs-list">https://ngrok.com/docs/api#api-reserved-addrs-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[ReservedAddrList]] =
    apiClient
      .sendRequest[ReservedAddrList](
        NgrokApiClient.HttpMethod.Get,
        "/reserved_addrs",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update the attributes of a reserved address.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-addrs-update">https://ngrok.com/docs/api#api-reserved-addrs-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of what this reserved address will be used for
    * @param metadata arbitrary user-defined machine-readable data of this reserved address. Optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[ReservedAddr] =
    apiClient.sendRequest[ReservedAddr](
      NgrokApiClient.HttpMethod.Patch,
      s"/reserved_addrs/$id",
      List.empty,
      Option(
        ReservedAddrsUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
