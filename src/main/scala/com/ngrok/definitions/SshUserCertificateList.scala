package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshUserCertificateList]] resource.
  *
  * @constructor create a new SshUserCertificateList.
  * @param sshUserCertificates the list of all ssh user certificates on this account
  * @param uri URI of the ssh user certificates list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class SshUserCertificateList(
  sshUserCertificates: List[SshUserCertificate],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI]
) extends Pageable

object SshUserCertificateList {
  implicit val encodeSshUserCertificateList: io.circe.Encoder[SshUserCertificateList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ssh_user_certificates", value.sshUserCertificates.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshUserCertificateList: io.circe.Decoder[SshUserCertificateList] = (c: io.circe.HCursor) =>
    for {
      sshUserCertificates <- c.downField("ssh_user_certificates").as[List[SshUserCertificate]]
      uri                 <- c.downField("uri").as[java.net.URI]
      nextPageUri         <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield SshUserCertificateList(
      sshUserCertificates,
      uri,
      nextPageUri
    )
}
