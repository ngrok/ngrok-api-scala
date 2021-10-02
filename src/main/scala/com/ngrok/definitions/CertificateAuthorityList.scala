package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[CertificateAuthorityList]] resource.
  *
  * @constructor create a new CertificateAuthorityList.
  * @param certificateAuthorities the list of all certificate authorities on this account
  * @param uri URI of the certificates authorities list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class CertificateAuthorityList(
  certificateAuthorities: List[CertificateAuthority],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object CertificateAuthorityList {
  implicit val encodeCertificateAuthorityList: io.circe.Encoder[CertificateAuthorityList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("certificate_authorities", value.certificateAuthorities.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeCertificateAuthorityList: io.circe.Decoder[CertificateAuthorityList] = (c: io.circe.HCursor) =>
    for {
      certificateAuthorities <- c.downField("certificate_authorities").as[List[CertificateAuthority]]
      uri                    <- c.downField("uri").as[java.net.URI]
      nextPageUri            <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield CertificateAuthorityList(
      certificateAuthorities,
      uri,
      nextPageUri
    )
}
