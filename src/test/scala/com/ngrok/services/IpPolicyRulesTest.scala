package com.ngrok.services

import java.net.URI
import java.time.OffsetDateTime

import com.github.tomakehurst.wiremock.client.WireMock._
import com.linecorp.armeria.common.HttpHeaderNames
import com.ngrok.Version
import com.ngrok.definitions.{IpPolicy, IpPolicyRule, Ref}
import io.circe.syntax._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.time.{Seconds, Span}
import support.TestBase

import scala.util.Try

object IpPolicyRulesTest {
  private val mockIpPolicy = IpPolicy(
    id = "some-id",
    uri = new URI("https://api.ngrok.com/ip_policies/some-id"),
    createdAt = OffsetDateTime.now,
    description = "",
    metadata = ""
  )

  private val mockIpPolicyRule = IpPolicyRule(
    id = "some-rule-id",
    uri = new URI("https://api.ngrok.com/ip_policy_rules/some-rule-id"),
    createdAt = OffsetDateTime.now,
    description = "",
    metadata = "",
    cidr = "10.1.2.0/24",
    ipPolicy = Ref(id = "some-id", uri = new URI("https://api.ngrok.com/ip_policy/some-id")),
    action = "allow"
  )
}

class IpPolicyRulesTest extends AnyFreeSpec with Matchers with ScalaFutures with TestBase {
  import IpPolicyRulesTest._

  implicit private val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(4, Seconds))

  override def beforeEach(): Unit = {
    super.beforeEach()

    if (!UseLiveApi) {
      wireMock.stubFor(
        post(urlPathEqualTo("/ip_policies"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
          .withRequestBody(equalToJson("{}"))
          .willReturn(
            ok(mockIpPolicy.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
          )
      )

      wireMock.stubFor(
        delete(urlPathEqualTo(s"/ip_policies/${mockIpPolicy.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(noContent())
      )

      wireMock.stubFor(
        post(urlPathEqualTo("/ip_policy_rules"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
          .withRequestBody(equalToJson(Map(
            "ip_policy_id" -> mockIpPolicy.id,
            "cidr" -> mockIpPolicyRule.cidr,
            "action" -> mockIpPolicyRule.action
          ).asJson.noSpaces))
          .willReturn(
            ok(mockIpPolicyRule.asJson.noSpaces)
              .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
          )
      )

      wireMock.stubFor(
        delete(urlPathEqualTo(s"/ip_policy_rules/${mockIpPolicyRule.id}"))
          .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo(s"Bearer $FakeApiSecret"))
          .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo(UserAgent))
          .withHeader("ngrok-version", equalTo(Version.ApiVersion))
          .willReturn(noContent())
      )
    }
  }

  "IP policies and rules can be created and deleted" in {
    val policyId = createIpPolicy()
    try {
      val ruleId = createIpPolicyRule(policyId)
      deleteIpPolicyRule(ruleId)
    } finally {
      Try(deleteIpPolicy(policyId))
    }
  }

  private def createIpPolicy(): String = ngrok.ipPolicies.create().futureValue.id
  private def deleteIpPolicy(id: String): Unit = ngrok.ipPolicies.delete(id).futureValue

  private def createIpPolicyRule(policyId: String): String = {
    val rule = ngrok.ipPolicyRules.create("10.1.2.0/24", policyId, "allow").futureValue
    rule.ipPolicy.id mustBe policyId
    rule.action mustBe "allow"
    rule.cidr mustBe "10.1.2.0/24"
    rule.id
  }

  private def deleteIpPolicyRule(id: String): Unit = ngrok.ipPolicyRules.delete(id).futureValue
}
