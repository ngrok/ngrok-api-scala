package com.ngrok

import java.net.URI

import io.circe.Encoder

package object services {
  implicit private[services] val encodeURI: Encoder[URI] = Encoder.encodeString.contramap(_.toString)
}
