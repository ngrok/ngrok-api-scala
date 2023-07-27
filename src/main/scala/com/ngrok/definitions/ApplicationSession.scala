/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/**
 * A class encapsulating the [[ApplicationSession]] resource.
 *
 * @constructor create a new ApplicationSession.
 * @param id unique application session resource identifier
 * @param uri URI of the application session API resource
 * @param publicUrl URL of the hostport served by this endpoint
 * @param browserSession browser session details of the application session
 * @param applicationUser application user this session is associated with
 * @param createdAt timestamp when the user was created in RFC 3339 format
 * @param lastActive timestamp when the user was last active in RFC 3339 format
 * @param expiresAt timestamp when session expires in RFC 3339 format
 * @param endpoint ephemeral endpoint this session is associated with
 * @param edge edge this session is associated with, null if the endpoint is agent-initiated
 * @param route route this session is associated with, null if the endpoint is agent-initiated
 */
final case class ApplicationSession(
  id: String,
  uri: java.net.URI,
  publicUrl: java.net.URI,
  browserSession: BrowserSession,
  applicationUser: Option[Ref] = None,
  createdAt: java.time.OffsetDateTime,
  lastActive: java.time.OffsetDateTime,
  expiresAt: java.time.OffsetDateTime,
  endpoint: Option[Ref] = None,
  edge: Option[Ref] = None,
  route: Option[Ref] = None,
)

object ApplicationSession {
  implicit val encodeApplicationSession: io.circe.Encoder[ApplicationSession] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson)),
      Option(("public_url", value.publicUrl.asJson)),
      Option(("browser_session", value.browserSession.asJson)),
      value.applicationUser.map(_.asJson).map(("application_user", _)),
      Option(("created_at", value.createdAt.asJson)),
      Option(("last_active", value.lastActive.asJson)),
      Option(("expires_at", value.expiresAt.asJson)),
      value.endpoint.map(_.asJson).map(("endpoint", _)),
      value.edge.map(_.asJson).map(("edge", _)),
      value.route.map(_.asJson).map(("route", _)),
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApplicationSession: io.circe.Decoder[ApplicationSession] = (c: io.circe.HCursor) => for {
    id <- c.downField("id").as[String]
    uri <- c.downField("uri").as[java.net.URI]
    publicUrl <- c.downField("public_url").as[java.net.URI]
    browserSession <- c.downField("browser_session").as[BrowserSession]
    applicationUser <- c.downField("application_user").as[Option[Ref]]
    createdAt <- c.downField("created_at").as[java.time.OffsetDateTime]
    lastActive <- c.downField("last_active").as[java.time.OffsetDateTime]
    expiresAt <- c.downField("expires_at").as[java.time.OffsetDateTime]
    endpoint <- c.downField("endpoint").as[Option[Ref]]
    edge <- c.downField("edge").as[Option[Ref]]
    route <- c.downField("route").as[Option[Ref]]
  } yield ApplicationSession(
    id,
    uri,
    publicUrl,
    browserSession,
    applicationUser,
    createdAt,
    lastActive,
    expiresAt,
    endpoint,
    edge,
    route,
  )
}
