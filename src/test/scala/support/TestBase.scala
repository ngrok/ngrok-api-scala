package support

import java.net.URI

import com.ngrok.{DefaultNgrokApiClient, Ngrok, NgrokApiClient, Version}
import org.scalatest.Suite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Properties

trait TestBase extends WireMockSupport { this: Suite =>
  protected val FakeApiSecret: String = "s3kr1t"
  protected val UseLiveApi: Boolean   = Option(System.getenv("TEST_NO_MOCK")).exists(java.lang.Boolean.parseBoolean)
  protected val UserAgent: String     = s"ngrok-api-scala/${Version.ClientVersion}/${Properties.versionNumberString}"

  protected def ngrok: Ngrok = Ngrok(
    DefaultNgrokApiClient(
      apiKey = if (UseLiveApi) System.getenv("NGROK_API_KEY") else FakeApiSecret,
      baseUri = Option(if (UseLiveApi) NgrokApiClient.DefaultBaseUri else URI.create(wireMock.baseUrl()))
    )
  )
}
