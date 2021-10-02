package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshCertificateAuthorityList]] resource.
  *
  * @constructor create a new SshCertificateAuthorityList.
  * @param sshCertificateAuthorities the list of all certificate authorities on this account
  * @param uri URI of the certificates authorities list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class SshCertificateAuthorityList(
  sshCertificateAuthorities: List[SshCertificateAuthority],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object SshCertificateAuthorityList {
  implicit val encodeSshCertificateAuthorityList: io.circe.Encoder[SshCertificateAuthorityList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ssh_certificate_authorities", value.sshCertificateAuthorities.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshCertificateAuthorityList: io.circe.Decoder[SshCertificateAuthorityList] =
    (c: io.circe.HCursor) =>
      for {
        sshCertificateAuthorities <- c.downField("ssh_certificate_authorities").as[List[SshCertificateAuthority]]
        uri                       <- c.downField("uri").as[java.net.URI]
        nextPageUri               <- c.downField("next_page_uri").as[Option[java.net.URI]]
      } yield SshCertificateAuthorityList(
        sshCertificateAuthorities,
        uri,
        nextPageUri
      )
}
