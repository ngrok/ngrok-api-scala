package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object FailoverBackends {
  private case class FailoverBackendsCreate(
    description: Option[String],
    metadata: Option[String],
    backends: Option[List[String]]
  )

  private object FailoverBackendsCreate {
    implicit val encodeFailoverBackendsCreate: Encoder[FailoverBackendsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backends.map(_.asJson).map(("backends", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class FailoverBackendsUpdate(
    description: Option[String],
    metadata: Option[String],
    backends: Option[List[String]]
  )

  private object FailoverBackendsUpdate {
    implicit val encodeFailoverBackendsUpdate: Encoder[FailoverBackendsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backends.map(_.asJson).map(("backends", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** A Failover backend defines failover behavior within a list of referenced
  *  backends. Traffic is sent to the first backend in the list. If that backend
  *  is offline or no connection can be established, ngrok attempts to connect to
  *  the next backend in the list until one is successful.
  *
  * See also <a href="https://ngrok.com/docs/api#api-failover-backends">https://ngrok.com/docs/api#api-failover-backends</a>.
  */
class FailoverBackends private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import FailoverBackends._

  /** Create a new Failover backend
    *
    * See also <a href="https://ngrok.com/docs/api#api-failover-backends-create">https://ngrok.com/docs/api#api-failover-backends-create</a>.
    *
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param backends the ids of the child backends in order
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    backends: Option[List[String]] = None
  ): Future[FailoverBackend] =
    apiClient.sendRequest[FailoverBackend](
      NgrokApiClient.HttpMethod.Post,
      "/backends/failover",
      List.empty,
      Option(
        FailoverBackendsCreate(
          description,
          metadata,
          backends
        ).asJson
      )
    )

  /** Delete a Failover backend by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-failover-backends-delete">https://ngrok.com/docs/api#api-failover-backends-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/backends/failover/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a Failover backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-failover-backends-get">https://ngrok.com/docs/api#api-failover-backends-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[FailoverBackend] =
    apiClient.sendRequest[FailoverBackend](
      NgrokApiClient.HttpMethod.Get,
      s"/backends/failover/$id",
      List.empty,
      Option.empty
    )

  /** List all Failover backends on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-failover-backends-list">https://ngrok.com/docs/api#api-failover-backends-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[FailoverBackendList]] =
    apiClient
      .sendRequest[FailoverBackendList](
        NgrokApiClient.HttpMethod.Get,
        "/backends/failover",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update Failover backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-failover-backends-update">https://ngrok.com/docs/api#api-failover-backends-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param backends the ids of the child backends in order
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    backends: Option[List[String]] = None
  ): Future[FailoverBackend] =
    apiClient.sendRequest[FailoverBackend](
      NgrokApiClient.HttpMethod.Patch,
      s"/backends/failover/$id",
      List.empty,
      Option(
        FailoverBackendsUpdate(
          description,
          metadata,
          backends
        ).asJson
      )
    )

}
