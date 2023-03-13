package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EventDestinations {
  private case class EventDestinationsCreate(
    metadata: Option[String],
    description: Option[String],
    format: Option[String],
    target: Option[EventTarget]
  )

  private object EventDestinationsCreate {
    implicit val encodeEventDestinationsCreate: Encoder[EventDestinationsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.format.map(_.asJson).map(("format", _)),
          value.target.map(_.asJson).map(("target", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class EventDestinationsUpdate(
    metadata: Option[String],
    description: Option[String],
    format: Option[String],
    target: Option[EventTarget]
  )

  private object EventDestinationsUpdate {
    implicit val encodeEventDestinationsUpdate: Encoder[EventDestinationsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.format.map(_.asJson).map(("format", _)),
          value.target.map(_.asJson).map(("target", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EventDestinations]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-event-destinations">https://ngrok.com/docs/api#api-event-destinations</a>.
  */
class EventDestinations private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EventDestinations._

  /** Create a new Event Destination. It will not apply to anything until it is
    * associated with an Event Subscription.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations-create">https://ngrok.com/docs/api#api-event-destinations-create</a>.
    *
    * @param metadata Arbitrary user-defined machine-readable data of this Event Destination. Optional, max 4096 bytes.
    * @param description Human-readable description of the Event Destination. Optional, max 255 bytes.
    * @param format The output format you would like to serialize events into when sending to their target. Currently the only accepted value is <code>JSON</code>.
    * @param target An object that encapsulates where and how to send your events. An event destination must contain exactly one of the following objects, leaving the rest null: <code>kinesis</code>, <code>firehose</code>, <code>cloudwatch_logs</code>, or <code>s3</code>.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    metadata: Option[String] = None,
    description: Option[String] = None,
    format: Option[String] = None,
    target: Option[EventTarget] = None
  ): Future[EventDestination] =
    apiClient.sendRequest[EventDestination](
      NgrokApiClient.HttpMethod.Post,
      "/event_destinations",
      List.empty,
      Option(
        EventDestinationsCreate(
          metadata,
          description,
          format,
          target
        ).asJson
      )
    )

  /** Delete an Event Destination. If the Event Destination is still referenced by an
    * Event Subscription.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations-delete">https://ngrok.com/docs/api#api-event-destinations-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/event_destinations/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an Event Destination by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations-get">https://ngrok.com/docs/api#api-event-destinations-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EventDestination] =
    apiClient.sendRequest[EventDestination](
      NgrokApiClient.HttpMethod.Get,
      s"/event_destinations/$id",
      List.empty,
      Option.empty
    )

  /** List all Event Destinations on this account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations-list">https://ngrok.com/docs/api#api-event-destinations-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[EventDestinationList]] =
    apiClient
      .sendRequest[EventDestinationList](
        NgrokApiClient.HttpMethod.Get,
        "/event_destinations",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an Event Destination.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-destinations-update">https://ngrok.com/docs/api#api-event-destinations-update</a>.
    *
    * @param id Unique identifier for this Event Destination.
    * @param metadata Arbitrary user-defined machine-readable data of this Event Destination. Optional, max 4096 bytes.
    * @param description Human-readable description of the Event Destination. Optional, max 255 bytes.
    * @param format The output format you would like to serialize events into when sending to their target. Currently the only accepted value is <code>JSON</code>.
    * @param target An object that encapsulates where and how to send your events. An event destination must contain exactly one of the following objects, leaving the rest null: <code>kinesis</code>, <code>firehose</code>, <code>cloudwatch_logs</code>, or <code>s3</code>.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    metadata: Option[String] = None,
    description: Option[String] = None,
    format: Option[String] = None,
    target: Option[EventTarget] = None
  ): Future[EventDestination] =
    apiClient.sendRequest[EventDestination](
      NgrokApiClient.HttpMethod.Patch,
      s"/event_destinations/$id",
      List.empty,
      Option(
        EventDestinationsUpdate(
          metadata,
          description,
          format,
          target
        ).asJson
      )
    )

}
