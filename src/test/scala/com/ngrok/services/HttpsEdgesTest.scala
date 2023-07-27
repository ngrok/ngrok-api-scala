/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import java.net.URI
import java.time.OffsetDateTime

import com.github.tomakehurst.wiremock.client.WireMock._
import com.linecorp.armeria.common.HttpHeaderNames
import com.ngrok.Version
import com.ngrok.definitions.{HttpsEdge, HttpsEdgeList}
import io.circe.syntax._
import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.time.{Seconds, Span}
import support.TestBase

import scala.util.Try

object HttpsEdgesTest {
  private val liveHostport = Option(System.getenv("NGROK_HOSTPORT"))
  private val testHostport = "test-hostport-123.ngrok.io:443"

  private val mockEdge = HttpsEdge(
    id = "some-id",
    description = "",
    metadata = "test scala client",
    createdAt = OffsetDateTime.now,
    uri = new URI("https://api.ngrok.com/edges/https/some-id"),
    hostports = Some(List(testHostport)),
    routes = List.empty
  )

  private val mockEdgeList = HttpsEdgeList(
    httpsEdges = List(mockEdge),
    uri = new URI("https://api.ngrok.com/edges/https")
  )
}

class HttpsEdgesTest extends AnyFreeSpec with Matchers with ScalaFutures with OptionValues with TestBase {
  import HttpsEdgesTest._

  implicit private val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(10, Seconds))

  private val testHostports = if (UseLiveApi) liveHostport.toList else List(testHostport)

  override def beforeEach(): Unit = {
    super.beforeEach()

    if (!UseLiveApi) {
      wireMock.stubFor(
        post(urlPathEqualTo("/edges/https"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
          .withRequestBody(
            equalToJson(
              Map(
                "metadata"  -> mockEdge.metadata.asJson,
                "hostports" -> testHostports.asJson
              ).asJson.noSpaces
            )
          )
          .willReturn(
            ok(mockEdge.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
          )
      )

      wireMock.stubFor(
        get(urlPathEqualTo("/edges/https"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(
            ok(mockEdgeList.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
          )
      )

      wireMock.stubFor(
        delete(urlPathEqualTo(s"/edges/https/${mockEdge.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(noContent())
      )
    }
  }

  "HTTPS Edges can be created and listed" in {
    if (UseLiveApi) {
      if (liveHostport.isEmpty) {
        fail("Please set NGROK_HOSTPORT to a valid endpoint HOST:PORT before running this test live")
      }
    }

    val edgesId = createHttpsEdge(testHostports)
    try {
      listHttpsEdges(edgesId, testHostports)
    } finally {
      Try(deleteHttpsEdge(edgesId))
    }
  }

  private def createHttpsEdge(hostports: List[String]): String = {
    val edge = ngrok.edges.https
      .create(
        metadata = Some(mockEdge.metadata),
        hostports = hostports
      )
      .futureValue
    edge.hostports mustBe Some(hostports)
    edge.id
  }

  private def listHttpsEdges(expectedId: String, expectedHostports: List[String]): Unit = {
    val edge = ngrok.edges.https.list().futureValue.page.httpsEdges.find(_.metadata == mockEdge.metadata).value
    edge.id mustBe expectedId
    edge.hostports mustBe Some(expectedHostports)
  }

  private def deleteHttpsEdge(id: String): Unit = ngrok.edges.https.delete(id).futureValue
}
