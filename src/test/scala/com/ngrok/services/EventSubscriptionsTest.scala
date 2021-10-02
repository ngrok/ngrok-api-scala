package com.ngrok.services

import java.net.URI
import java.time.{Duration, OffsetDateTime}

import com.github.tomakehurst.wiremock.client.WireMock._
import com.linecorp.armeria.common.HttpHeaderNames
import com.ngrok.definitions.{
  AwsAuth,
  AwsRole,
  EventDestination,
  EventSource,
  EventSourceReplace,
  EventSubscription,
  EventSubscriptionList,
  EventTarget,
  EventTargetCloudwatchLogs,
  NgrokApiErrorHttpStatus,
  Ref
}
import com.ngrok.{NgrokApiClient, Version}
import io.circe.syntax._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{OptionValues, TryValues}
import support.TestBase

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import scala.util.control.NonFatal

object EventSubscriptionsTest {
  private val eventDestination = EventDestination(
    id = "9876543abcdefg",
    metadata = "Some extra metadata about this destination",
    createdAt = OffsetDateTime.now,
    description = "This is a cool destination",
    format = "json",
    target = EventTarget(cloudwatchLogs =
      Some(
        EventTargetCloudwatchLogs(
          AwsAuth(role = Some(AwsRole("arn:aws:iam::12345:role/my-role-name"))),
          "arn:aws:logs:us-east-1:12345:log-grp:my-log-grp"
        )
      )
    ),
    uri = URI.create("https://api.ngrok.com/event_destinations/9876543abcdefg")
  )

  private val eventSource = EventSource(
    `type` = "api_key_created.v0",
    uri = URI.create("https://api.ngrok.com/event_sources/1802749824798")
  )

  private val eventDestinationRef = Ref(
    id = eventDestination.id,
    uri = eventDestination.uri
  )

  private val eventDestinationCreate = Map(
    "description" -> eventDestination.description.asJson,
    "metadata"    -> eventDestination.metadata.asJson,
    "format"      -> eventDestination.format.asJson,
    "target"      -> eventDestination.target.asJson
  )
}

class EventSubscriptionsTest
  extends AnyFreeSpec
  with Matchers
  with ScalaFutures
  with OptionValues
  with TryValues
  with TestBase {
  import EventSubscriptionsTest._

  implicit private val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(4, Seconds))

  private val eventSubscription = EventSubscription(
    id = "abcdef123456",
    uri = URI.create("https://api.ngrok.com/event_subscriptions/abcdef123456"),
    createdAt = OffsetDateTime.now,
    metadata = "this event subscription is quite meta",
    description = "this is a great event subscription",
    sources = List(eventSource),
    if (UseLiveApi) List.empty else List(eventDestinationRef)
  )

  private val eventSubscriptionCreate = Map(
    "description"     -> eventSubscription.description.asJson,
    "metadata"        -> eventSubscription.metadata.asJson,
    "sources"         -> eventSubscription.sources.map(source => EventSourceReplace(source.`type`)).asJson,
    "destination_ids" -> eventSubscription.destinations.map(_.id).asJson
  )

  private val eventSubscriptionUpdated = eventSubscription.copy(description = "some updated description")

  private val eventSubscriptionUpdate = Map(
    "description" -> eventSubscriptionUpdated.description.asJson
  )

  private val eventSubscriptionList = EventSubscriptionList(
    eventSubscriptions = List(eventSubscription),
    uri = URI.create("https://api.ngrok.com/event_subscriptions"),
    nextPageUri = None
  )

  override def beforeEach(): Unit = {
    super.beforeEach()

    if (!UseLiveApi) {
      wireMock.resetAll()

      wireMock.stubFor(
        post(urlPathEqualTo("/event_destinations"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, containing("application/json"))
          .withRequestBody(equalToJson(eventDestinationCreate.asJson.noSpaces))
          .willReturn(
            ok(eventDestination.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, "application/json")
          )
      )

      wireMock.stubFor(
        delete(urlPathEqualTo(s"/event_destinations/${eventDestination.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(noContent)
      )

      wireMock.stubFor(
        post(urlPathEqualTo("/event_subscriptions"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, containing("application/json"))
          .withRequestBody(equalToJson(eventSubscriptionCreate.asJson.noSpaces))
          .willReturn(
            ok(eventSubscription.asJson.noSpaces).withHeader(HttpHeaderNames.CONTENT_TYPE.toString, "application/json")
          )
      )

      wireMock.stubFor(
        get(urlPathEqualTo(s"/event_subscriptions/${eventSubscription.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(
            ok(eventSubscription.asJson.noSpaces).withHeader(HttpHeaderNames.CONTENT_TYPE.toString, "application/json")
          )
      )

      wireMock.stubFor(
        get(urlPathEqualTo("/event_subscriptions"))
          .withQueryParam("limit", equalTo("10"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(
            ok(eventSubscriptionList.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, "application/json")
          )
      )

      wireMock.stubFor(
        patch(urlPathEqualTo(s"/event_subscriptions/${eventSubscription.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, containing("application/json"))
          .withRequestBody(equalToJson(eventSubscriptionUpdate.asJson.noSpaces))
          .willReturn(
            ok(eventSubscriptionUpdated.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString, "application/json")
          )
      )

      wireMock.stubFor(
        delete(urlPathEqualTo(s"/event_subscriptions/${eventSubscription.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString, equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString, equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(noContent)
      )
    }
  }

  "EventSubscriptions client can successfully issue all API operations" in {
    // Creating an event destination triggers server-side validation that the AWS resources
    // specified in the destination exist.  Since we don't have them, we'll leave the
    // destination out of our event subscription.
    val eventDestinationIds =
      if (UseLiveApi) List.empty
      else List(testCreateEventDestination())

    val id = testCreateEventSubscription(eventDestinationIds)
    try {
      testGetEventSubscription(id, eventDestinationIds)
      testListEventSubscriptions(id, eventDestinationIds)
      testUpdateEventSubscription(id, eventDestinationIds)
      testDeleteEventSubscription(id)
      eventDestinationIds.foreach(testDeleteEventDestination)
    } catch {
      case NonFatal(e) =>
        if (UseLiveApi) {
          // Make an attempt to clean things up
          ngrok.eventSubscriptions.delete(id).futureValue
        }
        throw e
    }

  }

  private def testCreateEventSubscription(eventDestinationIds: List[String]): String = {
    val createdEventSubscription = ngrok.eventSubscriptions
      .create(
        description = Some(eventSubscription.description),
        metadata = Some(eventSubscription.metadata),
        sources = Some(eventSubscription.sources.map(source => EventSourceReplace(source.`type`))),
        destinationIds = Some(eventDestinationIds)
      )
      .futureValue
    assertEventSubscriptionFields(createdEventSubscription)
    createdEventSubscription.id
  }

  private def testGetEventSubscription(id: String, eventDestinationIds: List[String]): Unit = {
    val fetchedEventSubscription = ngrok.eventSubscriptions.get(id).futureValue
    assertEventSubscriptionFields(fetchedEventSubscription, eventDestinationIds)
  }

  private def testListEventSubscriptions(id: String, eventDestinationIds: List[String]): Unit = {
    val eventSubscriptionListPage = ngrok.eventSubscriptions.list(limit = Some("10")).futureValue
    val fetchedEventSubscription =
      if (UseLiveApi) {
        eventSubscriptionListPage.page.eventSubscriptions.filter(_.id == id).head
      } else {
        eventSubscriptionListPage.page.nextPageUri mustBe None
        eventSubscriptionListPage.next().futureValue mustBe None
        eventSubscriptionListPage.page.eventSubscriptions.head
      }
    assertEventSubscriptionFields(fetchedEventSubscription, eventDestinationIds)
  }

  private def testUpdateEventSubscription(id: String, eventDestinationIds: List[String]): Unit = {
    val updatedEventSubscription = ngrok.eventSubscriptions
      .update(
        id = id,
        description = Some(eventSubscriptionUpdated.description)
      )
      .futureValue
    assertEventSubscriptionFields(updatedEventSubscription, eventSubscriptionUpdated.description, eventDestinationIds)
  }

  private def testDeleteEventSubscription(id: String): Unit = {
    ngrok.eventSubscriptions.delete(id).futureValue
    if (UseLiveApi) {
      val getResult = Try(ngrok.eventSubscriptions.get(id).futureValue)
      getResult.failure.exception.getCause match {
        case NgrokApiErrorHttpStatus(404) => // good
        case other                        => fail(other)
      }
    }
  }

  private def testCreateEventDestination(): String = {
    val dest = ngrok.eventDestinations
      .create(
        description = Some(eventDestination.description),
        metadata = Some(eventDestination.metadata),
        format = Some(eventDestination.format),
        target = Some(eventDestination.target)
      )
      .futureValue
    dest.id
  }

  private def testDeleteEventDestination(id: String): Unit = {
    ngrok.eventDestinations.delete(id).futureValue
    if (UseLiveApi) {
      val getResult = Try(ngrok.eventDestinations.get(id).futureValue)
      getResult.failure.exception.getCause match {
        case NgrokApiErrorHttpStatus(404) => // good
        case other                        => fail(other)
      }
    }
  }

  private def assertEventSubscriptionFields(
    actualEventSubscription: EventSubscription,
    expectedDescription: String,
    expectedEventDestinationIds: List[String]
  ): Unit = {
    if (UseLiveApi) {
      actualEventSubscription.id mustNot be(null)
      actualEventSubscription.id mustNot be("")
      actualEventSubscription.uri.getHost mustBe NgrokApiClient.DefaultBaseUri.getHost
      assert(actualEventSubscription.createdAt.isAfter(OffsetDateTime.now.minus(Duration.ofMinutes(20))))
    } else {
      actualEventSubscription.id mustBe eventSubscription.id
      actualEventSubscription.uri mustBe eventSubscription.uri
      actualEventSubscription.createdAt mustBe eventSubscription.createdAt
    }
    actualEventSubscription.description mustBe expectedDescription
    actualEventSubscription.metadata mustBe eventSubscription.metadata
    actualEventSubscription.sources.length mustBe 1
    actualEventSubscription.sources.head.`type` mustBe eventSource.`type`
    actualEventSubscription.destinations.map(_.id) mustBe expectedEventDestinationIds
  }

  private def assertEventSubscriptionFields(
    actualEventSubscription: EventSubscription,
    expectedEventDestinationIds: List[String]
  ): Unit =
    assertEventSubscriptionFields(actualEventSubscription, eventSubscription.description, expectedEventDestinationIds)

  private def assertEventSubscriptionFields(
    actualEventSubscription: EventSubscription,
    expectedDescription: String
  ): Unit =
    assertEventSubscriptionFields(
      actualEventSubscription,
      expectedDescription,
      eventSubscription.destinations.map(_.id)
    )

  private def assertEventSubscriptionFields(actualEventSubscription: EventSubscription): Unit =
    assertEventSubscriptionFields(actualEventSubscription, eventSubscription.description)
}
