package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[HttpResponseBackend]] resource.
  *
  * @constructor create a new HttpResponseBackend.
  * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
  * @param uri URI of the HTTPResponseBackend API resource
  * @param createdAt timestamp when the backend was created, RFC 3339 format
  * @param description human-readable description of this backend. Optional
  * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
  * @param body body to return as fixed content
  * @param headers headers to return
  * @param statusCode status code to return
  */
final case class HttpResponseBackend(
  id: String,
  uri: String,
  createdAt: java.time.OffsetDateTime,
  description: String,
  metadata: String,
  body: String,
  headers: Map[String, String],
  statusCode: Int
)

object HttpResponseBackend {
  implicit val encodeHttpResponseBackend: io.circe.Encoder[HttpResponseBackend] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("id", value.id.asJson)),
        Option(("uri", value.uri.asJson)),
        Option(("created_at", value.createdAt.asJson)),
        Option(("description", value.description.asJson)),
        Option(("metadata", value.metadata.asJson)),
        Option(("body", value.body.asJson)),
        Option(("headers", value.headers.asJson)),
        Option(("status_code", value.statusCode.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeHttpResponseBackend: io.circe.Decoder[HttpResponseBackend] = (c: io.circe.HCursor) =>
    for {
      id          <- c.downField("id").as[String]
      uri         <- c.downField("uri").as[String]
      createdAt   <- c.downField("created_at").as[java.time.OffsetDateTime]
      description <- c.downField("description").as[String]
      metadata    <- c.downField("metadata").as[String]
      body        <- c.downField("body").as[String]
      headers     <- c.downField("headers").as[Map[String, String]]
      statusCode  <- c.downField("status_code").as[Int]
    } yield HttpResponseBackend(
      id,
      uri,
      createdAt,
      description,
      metadata,
      body,
      headers,
      statusCode
    )
}
