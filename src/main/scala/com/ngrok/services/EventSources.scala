package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EventSources {
  private case class EventSourcesCreate(
    `type`: Option[String]
  )

  private object EventSourcesCreate {
    implicit val encodeEventSourcesCreate: Encoder[EventSourcesCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.`type`.map(_.asJson).map(("type", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[EventSources]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-event-sources">https://ngrok.com/docs/api#api-event-sources</a>.
  */
class EventSources private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EventSources._

  /** Add an additional type for which this event subscription will trigger
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources-create">https://ngrok.com/docs/api#api-event-sources-create</a>.
    *
    * @param subscriptionId The unique identifier for the Event Subscription that this Event Source is attached to.
    * @param `type` Type of event for which an event subscription will trigger
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    subscriptionId: String,
    `type`: Option[String] = None
  ): Future[EventSource] =
    apiClient.sendRequest[EventSource](
      NgrokApiClient.HttpMethod.Post,
      s"/event_subscriptions/$subscriptionId/sources",
      List.empty,
      Option(
        EventSourcesCreate(
          `type`
        ).asJson
      )
    )

  /** Remove a type for which this event subscription will trigger
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources-delete">https://ngrok.com/docs/api#api-event-sources-delete</a>.
    *
    * @param subscriptionId The unique identifier for the Event Subscription that this Event Source is attached to.
    * @param `type` Type of event for which an event subscription will trigger
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    subscriptionId: String,
    `type`: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/event_subscriptions/$subscriptionId/sources/${`type`}",
      List.empty,
      Option.empty
    )

  /** Get the details for a given type that triggers for the given event subscription
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources-get">https://ngrok.com/docs/api#api-event-sources-get</a>.
    *
    * @param subscriptionId The unique identifier for the Event Subscription that this Event Source is attached to.
    * @param `type` Type of event for which an event subscription will trigger
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    subscriptionId: String,
    `type`: String
  ): Future[EventSource] =
    apiClient.sendRequest[EventSource](
      NgrokApiClient.HttpMethod.Get,
      s"/event_subscriptions/$subscriptionId/sources/${`type`}",
      List.empty,
      Option.empty
    )

  /** List the types for which this event subscription will trigger
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources-list">https://ngrok.com/docs/api#api-event-sources-list</a>.
    *
    * @param subscriptionId The unique identifier for the Event Subscription that this Event Source is attached to.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    subscriptionId: String
  ): Future[EventSourceList] =
    apiClient.sendRequest[EventSourceList](
      NgrokApiClient.HttpMethod.Get,
      s"/event_subscriptions/$subscriptionId/sources",
      List.empty,
      Option.empty
    )

  /** Update the type for which this event subscription will trigger
    *
    * See also <a href="https://ngrok.com/docs/api#api-event-sources-update">https://ngrok.com/docs/api#api-event-sources-update</a>.
    *
    * @param subscriptionId The unique identifier for the Event Subscription that this Event Source is attached to.
    * @param `type` Type of event for which an event subscription will trigger
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    subscriptionId: String,
    `type`: String
  ): Future[EventSource] =
    apiClient.sendRequest[EventSource](
      NgrokApiClient.HttpMethod.Patch,
      s"/event_subscriptions/$subscriptionId/sources/${`type`}",
      List.empty,
      Option.empty
    )

}
