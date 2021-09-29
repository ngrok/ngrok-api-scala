package com.ngrok.services

import java.net.URI
import java.time.OffsetDateTime

import com.github.tomakehurst.wiremock.client.WireMock._
import com.linecorp.armeria.common.HttpHeaderNames
import com.ngrok.Version
import com.ngrok.definitions.{ApiKey, ApiKeyList, NgrokApiError}
import io.circe.Json
import io.circe.syntax._
import org.scalatest.{OptionValues, TryValues}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.time.{Seconds, Span}
import support.TestBase

import scala.util.Try
import scala.util.control.NonFatal

object ApiKeysTest {
  private val apiKey = ApiKey(
    id = "abcdef123456",
    uri = URI.create("https://api.ngrok.com/api_keys/abcdef123456"),
    description = "this is a great API key",
    metadata = "this API key is quite meta",
    createdAt = OffsetDateTime.parse("2021-06-08T21:09:00-07:00"),
    token = Some("qwertyuiop")
  )
  private val apiKeyNoToken = apiKey.copy(token = None)
  private val apiKeyUpdated = apiKeyNoToken.copy(description = "updated description")

  private val apiKeyJsonFields: Map[String, Json] = Map(
    "id"          -> apiKey.id.asJson,
    "uri"         -> apiKey.uri.asJson,
    "description" -> apiKey.description.asJson,
    "metadata"    -> apiKey.metadata.asJson,
    "created_at"  -> apiKey.createdAt.asJson,
    "token"       -> apiKey.token.asJson
  )

  private val apiKeyList = ApiKeyList(
    keys = List(apiKeyNoToken),
    uri = URI.create("https://api.ngrok.com/api_keys"),
    nextPageUri = None
  )

  private val apiKeyCreate: Map[String, Json] = apiKeyJsonFields
    .filter { case (key, _) => key == "description" || key == "metadata" }
  private val apiKeyUpdate: Map[String, Json] = Map(
    "description" -> apiKeyUpdated.description.asJson
  )
}

class ApiKeysTest extends AnyFreeSpec with Matchers with ScalaFutures with OptionValues with TryValues with TestBase {
  import ApiKeysTest._

  implicit private val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(2, Seconds))

  override def beforeEach(): Unit = {
    super.beforeEach()

    wireMock.resetAll()

    wireMock.stubFor(
      post(urlPathEqualTo("/api_keys"))
        .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
        .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
        .withHeader("ngrok-version", equalTo(Version.ApiVersion))
        .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
        .withRequestBody(equalToJson(apiKeyCreate.asJson.noSpaces))
        .willReturn(
          ok(apiKey.asJson.noSpaces)
            .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
        )
    )

    wireMock.stubFor(
      get(urlPathEqualTo(s"/api_keys/${apiKey.id}"))
        .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
        .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
        .withHeader("ngrok-version", equalTo(Version.ApiVersion))
        .willReturn(
          ok(apiKeyNoToken.asJson.noSpaces)
            .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
        )
    )

    wireMock.stubFor(
      get(urlPathEqualTo("/api_keys"))
        .withQueryParam("limit", equalTo("10"))
        .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
        .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
        .withHeader("ngrok-version", equalTo(Version.ApiVersion))
        .willReturn(
          ok(apiKeyList.asJson.noSpaces)
            .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
        )
    )

    wireMock.stubFor(
      patch(urlPathEqualTo(s"/api_keys/${apiKey.id}"))
        .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
        .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
        .withHeader("ngrok-version", equalTo(Version.ApiVersion))
        .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
        .withRequestBody(equalToJson(apiKeyUpdate.asJson.noSpaces))
        .willReturn(
          ok(apiKeyUpdated.asJson.noSpaces)
            .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
        )
    )

    wireMock.stubFor(
      delete(urlPathEqualTo(s"/api_keys/${apiKey.id}"))
        .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
        .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(s"ngrok-api-client-scala/${Version.ClientVersion}"))
        .withHeader("ngrok-version", equalTo(Version.ApiVersion))
        .willReturn(noContent())
    )
  }

  "ApiKeys client can successfully issue all API operations" in {
    val id = testCreateApiKey()
    try {
      testGetApiKey(id)
      testListApiKeys(id)
      testUpdateApiKey(id)
      testDeleteApiKey(id)
    } catch {
      case NonFatal(e) =>
        if (UseLiveApi) {
          Try(testDeleteApiKey(id))
        }
        throw e
    }
  }

  private def testCreateApiKey(): String = {
    val newApiKey = ngrok.apiKeys.create(Some(apiKey.description), Some(apiKey.metadata)).futureValue
    assertApiKeyFields(newApiKey, apiKey, newApiKey.id)
    newApiKey.id
  }

  private def testGetApiKey(id: String): Unit = {
    val fetchedApiKey = ngrok.apiKeys.get(id).futureValue
    assertApiKeyFields(fetchedApiKey, apiKeyNoToken, id)
  }

  private def testListApiKeys(id: String): Unit = {
    val fetchedApiKeys = ngrok.apiKeys.list(limit = Some("10")).futureValue
    val fetchedApiKey = if (UseLiveApi) {
      fetchedApiKeys.page.keys.find(_.id == id).value
    } else {
      fetchedApiKeys.page.keys.length mustBe 1
      fetchedApiKeys.page.nextPageUri mustBe None
      fetchedApiKeys.page.keys.head
    }
    assertApiKeyFields(fetchedApiKey, apiKeyNoToken, id)
  }

  private def testUpdateApiKey(id: String): Unit = {
    val updatedApiKey = ngrok.apiKeys.update(id, description = Some(apiKeyUpdated.description)).futureValue
    assertApiKeyFields(updatedApiKey, apiKeyUpdated, id)
  }

  private def testDeleteApiKey(id: String): Unit = {
    ngrok.apiKeys.delete(id).futureValue
    if (UseLiveApi) {
      val getResult = Try(ngrok.apiKeys.get(id).futureValue)
      getResult.failure.exception.getCause match {
        case NgrokApiError(404) => // good
        case other              => fail(other)
      }
    }
  }

  private def assertApiKeyFields(actualApiKey: ApiKey, expectedApiKey: ApiKey, id: String): Unit =
    if (UseLiveApi) {
      actualApiKey.id mustBe id
      actualApiKey.description mustBe expectedApiKey.description
      actualApiKey.metadata mustBe expectedApiKey.metadata
      actualApiKey.uri mustBe URI.create(s"https://api.ngrok.com/api_keys/$id")
      if (expectedApiKey.token.isEmpty) {
        actualApiKey.token mustBe None
      } else {
        assert(actualApiKey.token.isDefined)
      }
    } else {
      actualApiKey mustBe expectedApiKey
    }
}
