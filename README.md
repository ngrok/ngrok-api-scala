# Unstable

This library is currently unstable. We know of rough edges
and are working to bring it to parity with our other API client
libraries. Please feel free to try it out and let us know if you find
it useful!

# ngrok API client library for Scala

This library wraps the [ngrok HTTP API](https://ngrok.com/docs/api) to
make it easier to consume in Scala.

## Usage

This library is published on Maven Central for [2.12
](https://search.maven.org/artifact/com.ngrok/ngrok-api-scala_2.12)
and [2.13
](https://search.maven.org/artifact/com.ngrok/ngrok-api-scala_2.13).

If using sbt, add the following dependency:

```scala
"com.ngrok" %% "ngrok-api-scala % "[use latest version]"
```

If using Maven, in your `pom.xml` file, add:

```xml
<dependencies>
  <dependency>
    <groupId>com.ngrok</groupId>
    <artifactId>ngrok-api-scala</artifactId>
    <version>${ngrok-api-scala.version}</version>
  </dependency>
</dependencies>
```

See the above URL for the latest version of the API client.

Versions of this library are published for Scala 2.12 and Scala 2.13
(the 2.13 version can be used with Scala 3).

## Documentation

All objects, methods and properties are documented with Scaladoc for
integration with an IDE like IntelliJ IDEA or Eclipse. You can also
[view the documentation online](https://scala-api.docs.ngrok.com/).

Beyond that, this readme is the best source of documentation for the
library.

### Versioning

This class library is published to Maven Central using semantic
versioning. Breaking changes to the API will only be released with a
bump of the major version number. Each released commit is tagged in
this repository.

No compatibility promises are made for versions < 1.0.0.

### Quickstart

First, use the ngrok dashboard to generate an API key. Store that in a
safe place. Inject it into your application using the environment
variable `NGROK_API_KEY`. The `Ngrok()` method will pull from that
environment variable. If you prefer, you can also pass the API key
explicitly.

#### Create an IP Policy that allows traffic from some subnets

```scala
import cats.implicits._
import com.ngrok._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Example extends App {
  val ngrok = Ngrok()

  Await.result(
    ngrok.ipPolicies.create(Some("allow")).flatMap(policy =>
      List("24.0.0.0/8", "12.0.0.0/8").traverse(
        cidr => ngrok.ipPolicyRules.create(cidr, policy.id)
      )
    ),
    1.second
  )
}
```

#### List all online tunnels

```scala
import cats.implicits._
import com.ngrok._
import com.ngrok.definitions._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

class Example extends App {
  val ngrok = Ngrok()

  println("Tunnels:")
  Await.result(
    ngrok.tunnels.list().map(printRecursively(ngrok, _)),
    5.second
  )

  private def printRecursively(ngrok: Ngrok, currentPage: Page[TunnelList]): Future[Unit] = {
    currentPage.page.tunnels.foreach(println)
    currentPage.next()
      .map(_
        .map(printRecursively(ngrok, _))
        .getOrElse(Future.successful(()))
      )
  }
}
```

### Conventions

Conventional usage of this package is to construct a root `Ngrok` object
using its `apply()` method. You can then access API resources using that
object. Do not construct the individual API resource client classes in
your application code.

You can also customize low-level behavior by instantiating the
`DefaultNgrokApiClient` yourself, and then using it to construct the
`Ngrok` instance. If you'd like to use a different HTTP library
entirely, you can even implement the `NgrokApiClient` interface
yourself.

```scala
import com.ngrok._

import java.net.URI

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Example extends App {
  // Create the root api client using an API key from the environment variable NGROK_API_KEY
  val defaultNgrok = Ngrok()

  // ... or create the root api client using an API key provided directly
  val defaultNgrokWithApiKey = Ngrok("my secret api key")

  // ... or create the root api client by customizing the low-level networking details
  val customApiClient = DefaultNgrokApiClient(
    apiKey = System.getenv("NGROK_API_KEY")
    baseUri = Some(URI.create("https://some-other-server.com"))
  )
  val ngrokWithCustomApiClient = Ngrok(customApiClient);

  // Clients for all api resources (like ip policies) are acccessible via methods on the root client
  val policy = Await.result(defaultNgrok.ipPolicies.get(policyId), 1.second)

  // Some api resources are 'namespaced' through another method
  val circuitBreaker = Await.result(defaultNgrok.pointcfgModule.circuitBreaker.get(endpointConfigId), 1.second)
}
```

### Paging

All list responses from the ngrok API are paged. All list response
objects implement the `Pageable` trait, and are wrapped in a `Page`
class, which has a `next()` method. Calling `next()` will asyncronously
request the next page. If no next page is available, an empty `Option`
will be returned inside the `Future`.

```scala
import com.ngrok._
import com.ngrok.definitions._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

class Example extends App {
  val ngrok = Ngrok()

  val credentials = Await.result(
    ngrok.credentials.list().flatMap(gatherRecursively(ngrok, List.empty, _)),
    1.second
  )
  println("Credentials:")
  credentials.foreach(println)

  private def gatherRecursively(ngrok: Ngrok, credentials: List[Credential], currentPage: Page[CredentialList]): Future[List[Credential]] {
    val updatedCredentials = credentials ++ currentPage.page.credentials
    currentPage.next().flatMap(_
        .map(gatherRecursively(ngrok, updatedCredentials, _))
        .getOrElse(Future.successful(updatedCredentials))
    )
  }
}
```

### Error Handling

All errors returned by the ngrok API are serialized as structured
payloads for easy error handling. If a structured error is returned by
the ngrok API, this library will return a failed `Future`
containing a `NgrokApiError`.

This object will allow you to check the unique ngrok error code and the
http status code of a failed response. Use the `errorCode` property
to check for unique ngrok error codes returned by the API. All error
codes are documented at
[https://ngrok.com/docs/errors](https://ngrok.com/docs/errors). There is
also an `isErrorCode()` method on the exception object to check against
multiple error codes. The `httpStatusCode` property can be used to
check not found errors.

Other non-structured errors encountered while making an API call from
e.g. networking or serialization failures are not wrapped in any way and
will bubble up as normal.

```scala
import com.ngrok._
import com.ngrok.definitions._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class Example extends App {
  val ngrok = Ngrok()

  Await.ready(
    ngrok.reservedDomains.create(
      name = System.getenv("NGROK_DOMAIN"),
      description = Some("example domain")
    ).onComplete({
      case Success(_) =>
        println("Successfully reserved domain.")

      case Failure(error: NgrokApiError) if error.isErrorCode("NGROK_ERR_402", "NGROK_ERR_403") =>
        println("Ignoring invalid wildcard domain.")

      case Failure(error: NgrokApiError) =>
        val errorCode = error.errorCode.getOrElse("unknown")
        println(s"API Error ($errorCode): ${error.message}")

      case Failure(error) =>
        println(s"Other error: ${error.message})
    }),
    1.second
  )
}
```

### Datatype Overrides

All datatype objects in the ngrok API library are case classes, which
properly override `equals()` and `hashCode()` so that the objects can be
compared. Similarly, they override `toString()` for more helpful pretty
printing of ngrok domain objects.

### Sync / Async Interfaces

Each API client operation returns a `Future[T]` and is asynchonous. If
you require a synchonous call, you can wrap the return value in
`Await.result()`.
