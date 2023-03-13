package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ApiKey]] resource.
  *
  * @constructor create a new ApiKey.
  * @param id unique API key resource identifier
  * @param uri URI to the API resource of this API key
  * @param description human-readable description of what uses the API key to authenticate. optional, max 255 bytes.
  * @param metadata arbitrary user-defined data of this API key. optional, max 4096 bytes
  * @param createdAt timestamp when the api key was created, RFC 3339 format
  * @param token the bearer token that can be placed into the Authorization header to authenticate request to the ngrok API. <strong>This value is only available one time, on the API response from key creation. Otherwise it is null.</strong>
  * @param ownerId If supplied at credential creation, ownership will be assigned to the specified User or Bot. Only admins may specify an owner other than themselves. Defaults to the authenticated User or Bot.
  */
final case class ApiKey(
  id: String,
  uri: java.net.URI,
  description: String,
  metadata: String,
  createdAt: java.time.OffsetDateTime,
  token: Option[String] = None,
  ownerId: Option[String] = None
)

object ApiKey {
  implicit val encodeApiKey: io.circe.Encoder[ApiKey] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("description", value.description.asJson)),
      Option(("metadata", value.metadata.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      value.token.map(_.asJson).map(("token", _)),
      value.ownerId.map(_.asJson).map(("owner_id", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApiKey: io.circe.Decoder[ApiKey] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[java.net.URI]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      token       <- c.downField("token").as[Option[String]]
      ownerId     <- c.downField("owner_id").as[Option[String]]
    } yield ApiKey(
      id,
      uri,
      description,
      metadata,
      createdAt,
      token,
      ownerId
    )
}
