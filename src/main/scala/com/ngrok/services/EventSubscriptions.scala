package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EventSubscriptions {
  private case class EventSubscriptionsCreate(
    metadata: Option[String],
    description: Option[String],
    sources: List[EventSourceReplace],
    destinationIds: List[String]
  )

  private object EventSubscriptionsCreate {
    implicit val encodeEventSubscriptionsCreate: Encoder[EventSubscriptionsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.description.map(_.asJson).map(("description", _)),
          if (value.sources.isEmpty) None else Option(("sources", value.sources.asJson)),
          if (value.destinationIds.isEmpty) None else Option(("destination_ids", value.destinationIds.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class EventSubscriptionsUpdate(
    metadata: Option[String],
    description: Option[String],
    sources: List[EventSourceReplace],
    destinationIds: List[String]
  )

  private object EventSubscriptionsUpdate {
    implicit val encodeEventSubscriptionsUpdate: Encoder[EventSubscriptionsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.description.map(_.asJson).map(("description", _)),
          if (value.sources.isEmpty) None else Option(("sources", value.sources.asJson)),
          if (value.destinationIds.isEmpty) None else Option(("destination_ids", value.destinationIds.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An API client for [[EventSubscriptions]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions">https://ngrok.com/docs/api#api-event-subscriptions</a>.
  */
class EventSubscriptions private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EventSubscriptions._

  /** Create an Event Subscription.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions-create">https://ngrok.com/docs/api#api-event-subscriptions-create</a>.
    *
    * @param metadata Arbitrary customer supplied information intended to be machine readable. Optional, max 4096 chars.
    * @param description Arbitrary customer supplied information intended to be human readable. Optional, max 255 chars.
    * @param sources Sources containing the types for which this event subscription will trigger
    * @param destinationIds A list of Event Destination IDs which should be used for this Event Subscription.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    metadata: Option[String] = None,
    description: Option[String] = None,
    sources: List[EventSourceReplace] = List.empty,
    destinationIds: List[String] = List.empty
  ): Future[EventSubscription] =
    apiClient.sendRequest[EventSubscription](
      NgrokApiClient.HttpMethod.Post,
      "/event_subscriptions",
      List.empty,
      Option(
        EventSubscriptionsCreate(
          metadata,
          description,
          sources,
          destinationIds
        ).asJson
      )
    )

  /** Delete an Event Subscription.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions-delete">https://ngrok.com/docs/api#api-event-subscriptions-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/event_subscriptions/$id",
      List.empty,
      Option.empty
    )

  /** Get an Event Subscription by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions-get">https://ngrok.com/docs/api#api-event-subscriptions-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EventSubscription] =
    apiClient.sendRequest[EventSubscription](
      NgrokApiClient.HttpMethod.Get,
      s"/event_subscriptions/$id",
      List.empty,
      Option.empty
    )

  /** List this Account&#39;s Event Subscriptions.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions-list">https://ngrok.com/docs/api#api-event-subscriptions-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[EventSubscriptionList]] =
    apiClient
      .sendRequest[EventSubscriptionList](
        NgrokApiClient.HttpMethod.Get,
        "/event_subscriptions",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update an Event Subscription.
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-subscriptions-update">https://ngrok.com/docs/api#api-event-subscriptions-update</a>.
    *
    * @param id Unique identifier for this Event Subscription.
    * @param metadata Arbitrary customer supplied information intended to be machine readable. Optional, max 4096 chars.
    * @param description Arbitrary customer supplied information intended to be human readable. Optional, max 255 chars.
    * @param sources Sources containing the types for which this event subscription will trigger
    * @param destinationIds A list of Event Destination IDs which should be used for this Event Subscription.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    metadata: Option[String] = None,
    description: Option[String] = None,
    sources: List[EventSourceReplace] = List.empty,
    destinationIds: List[String] = List.empty
  ): Future[EventSubscription] =
    apiClient.sendRequest[EventSubscription](
      NgrokApiClient.HttpMethod.Patch,
      s"/event_subscriptions/$id",
      List.empty,
      Option(
        EventSubscriptionsUpdate(
          metadata,
          description,
          sources,
          destinationIds
        ).asJson
      )
    )

}
