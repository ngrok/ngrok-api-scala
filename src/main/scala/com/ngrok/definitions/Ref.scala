package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[Ref]] resource.
  *
  * @constructor create a new Ref.
  * @param id a resource identifier
  * @param uri a uri for locating a resource
  */
final case class Ref(
  id: String,
  uri: java.net.URI
)

object Ref {
  implicit val encodeRef: io.circe.Encoder[Ref] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("id", value.id.asJson)),
      Option(("uri", value.uri.asJson))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeRef: io.circe.Decoder[Ref] = (c: io.circe.HCursor) =>
    for {
      id  <- c.downField("id").as[String]
      uri <- c.downField("uri").as[java.net.URI]
    } yield Ref(
      id,
      uri
    )
}
