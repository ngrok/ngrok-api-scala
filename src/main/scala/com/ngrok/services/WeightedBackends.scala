package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object WeightedBackends {
  private case class WeightedBackendsCreate(
    description: Option[String],
    metadata: Option[String],
    backends: Option[Map[String, Long]]
  )

  private object WeightedBackendsCreate {
    implicit val encodeWeightedBackendsCreate: Encoder[WeightedBackendsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backends.map(_.asJson).map(("backends", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class WeightedBackendsUpdate(
    description: Option[String],
    metadata: Option[String],
    backends: Option[Map[String, Long]]
  )

  private object WeightedBackendsUpdate {
    implicit val encodeWeightedBackendsUpdate: Encoder[WeightedBackendsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.backends.map(_.asJson).map(("backends", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** A Weighted Backend balances traffic among the referenced backends. Traffic
  *  is assigned proportionally to each based on its weight. The percentage of
  *  traffic is calculated by dividing a backend's weight by the sum of all
  *  weights.
  *
  * See also <a href="https://ngrok.com/docs/api#api-weighted-backends">https://ngrok.com/docs/api#api-weighted-backends</a>.
  */
class WeightedBackends private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import WeightedBackends._

  /** Create a new Weighted backend
    *
    * See also <a href="https://ngrok.com/docs/api#api-weighted-backends-create">https://ngrok.com/docs/api#api-weighted-backends-create</a>.
    *
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param backends the ids of the child backends to their weights (0-10000)
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    backends: Option[Map[String, Long]] = None
  ): Future[WeightedBackend] =
    apiClient.sendRequest[WeightedBackend](
      NgrokApiClient.HttpMethod.Post,
      "/backends/weighted",
      List.empty,
      Option(
        WeightedBackendsCreate(
          description,
          metadata,
          backends
        ).asJson
      )
    )

  /** Delete a Weighted backend by ID. TODO what if used?
    *
    * See also <a href="https://ngrok.com/docs/api#api-weighted-backends-delete">https://ngrok.com/docs/api#api-weighted-backends-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/backends/weighted/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a Weighted backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-weighted-backends-get">https://ngrok.com/docs/api#api-weighted-backends-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[WeightedBackend] =
    apiClient.sendRequest[WeightedBackend](
      NgrokApiClient.HttpMethod.Get,
      s"/backends/weighted/$id",
      List.empty,
      Option.empty
    )

  /** List all Weighted backends on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-weighted-backends-list">https://ngrok.com/docs/api#api-weighted-backends-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[WeightedBackendList]] =
    apiClient
      .sendRequest[WeightedBackendList](
        NgrokApiClient.HttpMethod.Get,
        "/backends/weighted",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update Weighted backend by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-weighted-backends-update">https://ngrok.com/docs/api#api-weighted-backends-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this backend. Optional
    * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
    * @param backends the ids of the child backends to their weights (0-10000)
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    backends: Option[Map[String, Long]] = None
  ): Future[WeightedBackend] =
    apiClient.sendRequest[WeightedBackend](
      NgrokApiClient.HttpMethod.Patch,
      s"/backends/weighted/$id",
      List.empty,
      Option(
        WeightedBackendsUpdate(
          description,
          metadata,
          backends
        ).asJson
      )
    )

}
