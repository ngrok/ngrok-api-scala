/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[ApplicationUser]] resource.
 *
 * @constructor create a new ApplicationUser.
 * @param id unique application user resource identifier
 * @param uri URI of the application user API resource
 * @param identityProvider identity provider that the user authenticated with
 * @param providerUserId unique user identifier
 * @param username user username
 * @param email user email
 * @param name user common name
 * @param createdAt timestamp when the user was created in RFC 3339 format
 * @param lastActive timestamp when the user was last active in RFC 3339 format
 * @param lastLogin timestamp when the user last signed-in in RFC 3339 format
 */
final case class ApplicationUser(
  id: String,
  uri: java.net.URI,
  identityProvider: IdentityProvider,
  providerUserId: String,
  username: String,
  email: String,
  name: String,
  createdAt: java.time.OffsetDateTime,
  lastActive: java.time.OffsetDateTime,
  lastLogin: java.time.OffsetDateTime,
)

object ApplicationUser {
  implicit val encodeApplicationUser: io.circe.Encoder[ApplicationUser] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("identity_provider", value.identityProvider.asJson)),
      Option(("provider_user_id", value.providerUserId.asJson)),
      Option(("username", value.username.asJson)),
      Option(("email", value.email.asJson)),
      Option(("name", value.name.asJson)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("last_active", value.lastActive.asJson)),
      Option(("last_login", value.lastLogin.asJson)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApplicationUser: io.circe.Decoder[ApplicationUser] = (c: io.circe.HCursor) => for {
    id <- c.downField("id").as[String]
    uri <- c.downField("uri").as[java.net.URI]
    identityProvider <- c.downField("identity_provider").as[IdentityProvider]
    providerUserId <- c.downField("provider_user_id").as[String]
    username <- c.downField("username").as[String]
    email <- c.downField("email").as[String]
    name <- c.downField("name").as[String]
    createdAt <- c.downField("created_at").as[java.time.OffsetDateTime]
    lastActive <- c.downField("last_active").as[java.time.OffsetDateTime]
    lastLogin <- c.downField("last_login").as[java.time.OffsetDateTime]
  } yield ApplicationUser(
    id,
    uri,
    identityProvider,
    providerUserId,
    username,
    email,
    name,
    createdAt,
    lastActive,
    lastLogin,
  )
}
