package support

import java.net.URI

import com.ngrok.{DefaultNgrokApiClient, Ngrok, NgrokApiClient}
import org.scalatest.Suite

import scala.concurrent.ExecutionContext.Implicits.global

trait TestBase extends WireMockSupport { this: Suite =>
  protected val FakeApiSecret: String = "s3kr1t"
  protected val UseLiveApi: Boolean   = Option(System.getenv("TEST_NO_MOCK")).exists(java.lang.Boolean.parseBoolean)

  protected def ngrok: Ngrok = Ngrok(
    DefaultNgrokApiClient(
      apiKey = if (UseLiveApi) System.getenv("NGROK_API_KEY") else FakeApiSecret,
      baseUri = Option(if (UseLiveApi) NgrokApiClient.DefaultBaseUri else URI.create(wireMock.baseUrl()))
    )
  )
}
