package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[EndpointWebhookValidation]] resource.
  *
  * @constructor create a new EndpointWebhookValidation.
  * @param enabled <code>true</code> if the module will be applied to traffic, <code>false</code> to disable. default <code>true</code> if unspecified
  * @param provider a string indicating which webhook provider will be sending webhooks to this endpoint. Value must be one of the supported providers: <code>SLACK</code>, <code>SNS</code>, <code>STRIPE</code>, <code>GITHUB</code>, <code>TWILIO</code>, <code>SHOPIFY</code>, <code>GITLAB</code>, <code>INTERCOM</code>, <code>SENDGRID</code>.
  * @param secret a string secret used to validate requests from the given provider. All providers except AWS SNS require a secret
  */
final case class EndpointWebhookValidation(
  enabled: Option[Boolean],
  provider: String,
  secret: String
)

object EndpointWebhookValidation {
  implicit val encodeEndpointWebhookValidation: io.circe.Encoder[EndpointWebhookValidation] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        value.enabled.map(_.asJson).map(("enabled", _)),
        Option(("provider", value.provider.asJson)),
        Option(("secret", value.secret.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeEndpointWebhookValidation: io.circe.Decoder[EndpointWebhookValidation] = (c: io.circe.HCursor) =>
    for {
      enabled  <- c.downField("enabled").as[Option[Boolean]]
      provider <- c.downField("provider").as[String]
      secret   <- c.downField("secret").as[String]
    } yield EndpointWebhookValidation(
      enabled,
      provider,
      secret
    )
}
