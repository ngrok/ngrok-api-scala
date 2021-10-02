package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshHostCertificateList]] resource.
  *
  * @constructor create a new SshHostCertificateList.
  * @param sshHostCertificates the list of all ssh host certificates on this account
  * @param uri URI of the ssh host certificates list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class SshHostCertificateList(
  sshHostCertificates: List[SshHostCertificate],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI] = None
) extends Pageable

object SshHostCertificateList {
  implicit val encodeSshHostCertificateList: io.circe.Encoder[SshHostCertificateList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ssh_host_certificates", value.sshHostCertificates.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshHostCertificateList: io.circe.Decoder[SshHostCertificateList] = (c: io.circe.HCursor) =>
    for {
      sshHostCertificates <- c.downField("ssh_host_certificates").as[List[SshHostCertificate]]
      uri                 <- c.downField("uri").as[java.net.URI]
      nextPageUri         <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield SshHostCertificateList(
      sshHostCertificates,
      uri,
      nextPageUri
    )
}
