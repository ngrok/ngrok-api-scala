package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointCircuitBreaker]] resource.
  *
  * @constructor create a new EndpointCircuitBreaker.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param trippedDuration Integer number of seconds after which the circuit is tripped to wait before re-evaluating upstream health
  * @param rollingWindow Integer number of seconds in the statistical rolling window that metrics are retained for.
  * @param numBuckets Integer number of buckets into which metrics are retained. Max 128.
  * @param volumeThreshold Integer number of requests in a rolling window that will trip the circuit. Helpful if traffic volume is low.
  * @param errorThresholdPercentage Error threshold percentage should be between 0 - 1.0, not 0-100.0
  */
final case class EndpointCircuitBreaker(
  enabled: Option[Boolean],
  trippedDuration: java.time.Duration,
  rollingWindow: Long,
  numBuckets: Long,
  volumeThreshold: Long,
  errorThresholdPercentage: Double
)

object EndpointCircuitBreaker {
  implicit val encodeEndpointCircuitBreaker: io.circe.Encoder[EndpointCircuitBreaker] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("tripped_duration", value.trippedDuration.asJson)),
        Option(("rolling_window", value.rollingWindow.asJson)),
        Option(("num_buckets", value.numBuckets.asJson)),
        Option(("volume_threshold", value.volumeThreshold.asJson)),
        Option(("error_threshold_percentage", value.errorThresholdPercentage.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointCircuitBreaker: io.circe.Decoder[EndpointCircuitBreaker] = (c: io.circe.HCursor) =>
    for {
      enabled                  <- c.downField("enabled").as[Option[Boolean]]
      trippedDuration          <- c.downField("tripped_duration").as[java.time.Duration]
      rollingWindow            <- c.downField("rolling_window").as[Long]
      numBuckets               <- c.downField("num_buckets").as[Long]
      volumeThreshold          <- c.downField("volume_threshold").as[Long]
      errorThresholdPercentage <- c.downField("error_threshold_percentage").as[Double]
    } yield EndpointCircuitBreaker(
      enabled,
      trippedDuration,
      rollingWindow,
      numBuckets,
      volumeThreshold,
      errorThresholdPercentage
    )
}
