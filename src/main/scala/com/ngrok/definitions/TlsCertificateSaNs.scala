package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TlsCertificateSaNs]] resource.
  *
  * @constructor create a new TlsCertificateSaNs.
  * @param dnsNames set of additional domains (including wildcards) this TLS certificate is valid for
  * @param ips set of IP addresses this TLS certificate is also valid for
  */
final case class TlsCertificateSaNs(
  dnsNames: List[String],
  ips: List[String]
)

object TlsCertificateSaNs {
  implicit val encodeTlsCertificateSaNs: io.circe.Encoder[TlsCertificateSaNs] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("dns_names", value.dnsNames.asJson)),
        Option(("ips", value.ips.asJson))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTlsCertificateSaNs: io.circe.Decoder[TlsCertificateSaNs] = (c: io.circe.HCursor) =>
    for {
      dnsNames <- c.downField("dns_names").as[Option[List[String]]]
      ips      <- c.downField("ips").as[Option[List[String]]]
    } yield TlsCertificateSaNs(
      dnsNames.getOrElse(List.empty),
      ips.getOrElse(List.empty)
    )
}
