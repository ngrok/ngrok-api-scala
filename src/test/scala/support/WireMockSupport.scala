/* Code generated for API Clients. DO NOT EDIT. */

package support

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.scalatest.{BeforeAndAfterEach, Suite}

trait WireMockSupport extends BeforeAndAfterEach { this: Suite =>
  protected val wireMock = new WireMockServer(wireMockConfig().dynamicPort)

  override def beforeEach(): Unit = wireMock.start()
  override def afterEach(): Unit  = wireMock.stop()
}
