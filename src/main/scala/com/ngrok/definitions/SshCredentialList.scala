package com.ngrok.definitions

import io.circe.syntax._

/** A class encapsulating the [[SshCredentialList]] resource.
  *
  * @constructor create a new SshCredentialList.
  * @param sshCredentials the list of all ssh credentials on this account
  * @param uri URI of the ssh credential list API resource
  * @param nextPageUri URI of the next page, or null if there is no next page
  */
final case class SshCredentialList(
  sshCredentials: List[SshCredential],
  uri: java.net.URI,
  nextPageUri: Option[java.net.URI]
) extends Pageable

object SshCredentialList {
  implicit val encodeSshCredentialList: io.circe.Encoder[SshCredentialList] =
    io.circe.Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("ssh_credentials", value.sshCredentials.asJson)),
        Option(("uri", value.uri.asJson)),
        value.nextPageUri.map(_.asJson).map(("next_page_uri", _))
      ).flatten.toMap.asJsonObject
    )

  implicit val decodeSshCredentialList: io.circe.Decoder[SshCredentialList] = (c: io.circe.HCursor) =>
    for {
      sshCredentials <- c.downField("ssh_credentials").as[List[SshCredential]]
      uri            <- c.downField("uri").as[java.net.URI]
      nextPageUri    <- c.downField("next_page_uri").as[Option[java.net.URI]]
    } yield SshCredentialList(
      sshCredentials,
      uri,
      nextPageUri
    )
}
