package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EventStreams {
  private case class EventStreamsCreate(
    metadata: Option[String],
    description: Option[String],
    fields: Option[List[String]],
    eventType: Option[String],
    destinationIds: Option[List[String]],
    samplingRate: Option[Double]
  )

  private object EventStreamsCreate {
    implicit val encodeEventStreamsCreate: Encoder[EventStreamsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.description.map(_.asJson).map(("description", _)),
        value.fields.map(_.asJson).map(("fields", _)),
        value.eventType.map(_.asJson).map(("event_type", _)),
        value.destinationIds.map(_.asJson).map(("destination_ids", _)),
        value.samplingRate.map(_.asJson).map(("sampling_rate", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class EventStreamsUpdate(
    metadata: Option[String],
    description: Option[String],
    fields: Option[List[String]],
    destinationIds: Option[List[String]],
    samplingRate: Option[Double]
  )

  private object EventStreamsUpdate {
    implicit val encodeEventStreamsUpdate: Encoder[EventStreamsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.description.map(_.asJson).map(("description", _)),
        value.fields.map(_.asJson).map(("fields", _)),
        value.destinationIds.map(_.asJson).map(("destination_ids", _)),
        value.samplingRate.map(_.asJson).map(("sampling_rate", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[EventStreams]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-event-streams">https://ngrok.com/docs/api#api-event-streams</a>.
  */
class EventStreams private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EventStreams._

  /** Create a new Event Stream. It will not apply to anything until you associate it
    * with one or more Endpoint Configs.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-streams-create">https://ngrok.com/docs/api#api-event-streams-create</a>.
    *
    * @param metadata Arbitrary user-defined machine-readable data of this Event Stream. Optional, max 4096 bytes.
    * @param description Human-readable description of the Event Stream. Optional, max 255 bytes.
    * @param fields A list of protocol-specific fields you want to collect on each event.
    * @param eventType The protocol that determines which events will be collected. Supported values are <code>tcp_connection_closed</code> and <code>http_request_complete</code>.
    * @param destinationIds A list of Event Destination IDs which should be used for this Event Stream. Event Streams are required to have at least one Event Destination.
    * @param samplingRate The percentage of all events you would like to capture. Valid values range from 0.01, representing 1% of all events to 1.00, representing 100% of all events.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    metadata: Option[String] = None,
    description: Option[String] = None,
    fields: Option[List[String]] = None,
    eventType: Option[String] = None,
    destinationIds: Option[List[String]] = None,
    samplingRate: Option[Double] = None
  ): Future[EventStream] =
    apiClient.sendRequest[EventStream](
      NgrokApiClient.HttpMethod.Post,
      "/event_streams",
      List.empty,
      Option(
        EventStreamsCreate(
          metadata,
          description,
          fields,
          eventType,
          destinationIds,
          samplingRate
        ).asJson
      )
    )

  /** Delete an Event Stream. Associated Event Destinations will be preserved.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-streams-delete">https://ngrok.com/docs/api#api-event-streams-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/event_streams/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an Event Stream by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-streams-get">https://ngrok.com/docs/api#api-event-streams-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EventStream] =
    apiClient.sendRequest[EventStream](
      NgrokApiClient.HttpMethod.Get,
      s"/event_streams/$id",
      List.empty,
      Option.empty
    )

  /** List all Event Streams available on this account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-streams-list">https://ngrok.com/docs/api#api-event-streams-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[EventStreamList]] =
    apiClient
      .sendRequest[EventStreamList](
        NgrokApiClient.HttpMethod.Get,
        "/event_streams",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an Event Stream by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-streams-update">https://ngrok.com/docs/api#api-event-streams-update</a>.
    *
    * @param id Unique identifier for this Event Stream.
    * @param metadata Arbitrary user-defined machine-readable data of this Event Stream. Optional, max 4096 bytes.
    * @param description Human-readable description of the Event Stream. Optional, max 255 bytes.
    * @param fields A list of protocol-specific fields you want to collect on each event.
    * @param destinationIds A list of Event Destination IDs which should be used for this Event Stream. Event Streams are required to have at least one Event Destination.
    * @param samplingRate The percentage of all events you would like to capture. Valid values range from 0.01, representing 1% of all events to 1.00, representing 100% of all events.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    metadata: Option[String] = None,
    description: Option[String] = None,
    fields: Option[List[String]] = None,
    destinationIds: Option[List[String]] = None,
    samplingRate: Option[Double] = None
  ): Future[EventStream] =
    apiClient.sendRequest[EventStream](
      NgrokApiClient.HttpMethod.Patch,
      s"/event_streams/$id",
      List.empty,
      Option(
        EventStreamsUpdate(
          metadata,
          description,
          fields,
          destinationIds,
          samplingRate
        ).asJson
      )
    )

}
