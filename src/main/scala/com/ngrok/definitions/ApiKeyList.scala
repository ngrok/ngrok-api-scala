package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[ApiKeyList]] resource.
  *
  * @constructor create a new ApiKeyList.
  * @param keys the list of API keys for this account
  * @param uri URI of the API keys list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class ApiKeyList(
  keys: List[ApiKey],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI]
) extends Pageable

object ApiKeyList {
  implicit val encodeApiKeyList: io.circe.Encoder[ApiKeyList] = io.circe.Encoder.encodeJsonObject.contramap(value =>
    List(
      Option(("keys", value.keys.asJson)),
      Option(("uri", value.uri.asJson)),
      value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
    ).flatten.toMap.asJsonObject
  )

  implicit val decodeApiKeyList: io.circe.Decoder[ApiKeyList] = (c: io.circe.HCursor) =>
    for {
      keys        <- c.downField("keys").as[List[ApiKey]]
      uri         <- c.downField("uri").as[java.net.URI]
      nextPageUri <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield ApiKeyList(
      keys,
      uri,
      nextPageUri
    )
}
