/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[TlsCertificateList]] resource.
  *
  * @constructor create a new TlsCertificateList.
  * @param tlsCertificates the list of all TLS certificates on this account
  * @param uri URI of the TLS certificates list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class TlsCertificateList(
  tlsCertificates: List[TlsCertificate],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object TlsCertificateList {
  implicit val encodeTlsCertificateList: io.circe.Encoder[TlsCertificateList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("tls_certificates", value.tlsCertificates.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeTlsCertificateList: io.circe.Decoder[TlsCertificateList] = (c: io.circe.HCursor) =>
    for {
      tlsCertificates <- c.downField("tls_certificates").as[Option[List[TlsCertificate]]]
      uri             <- c.downField("uri").as[java.net.URI]
      nextPageUri     <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield TlsCertificateList(
      tlsCertificates.getOrElse(List.empty),
      uri,
      nextPageUri
    )
}
