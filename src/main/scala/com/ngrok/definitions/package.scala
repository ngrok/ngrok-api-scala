/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok

import io.circe.{Decoder, Encoder}
import java.net.URI

import scala.util.Try

package object definitions {
  implicit private[definitions] val encodeURI: Encoder[URI] = Encoder.encodeString.contramap(_.toString)
  implicit private[definitions] val decodeURI: Decoder[URI] =
    Decoder.decodeString.emap(s => Try(URI.create(s)).toEither.swap.map(_.getMessage).swap)
}
