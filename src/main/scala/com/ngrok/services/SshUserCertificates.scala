package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object SshUserCertificates {
  private case class SshUserCertificatesCreate(
    sshCertificateAuthorityId: String,
    publicKey: String,
    principals: Option[List[String]],
    criticalOptions: Option[Map[String, String]],
    extensions: Option[Map[String, String]],
    validAfter: Option[java.time.OffsetDateTime],
    validUntil: Option[java.time.OffsetDateTime],
    description: Option[String],
    metadata: Option[String]
  )

  private object SshUserCertificatesCreate {
    implicit val encodeSshUserCertificatesCreate: Encoder[SshUserCertificatesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          Option(("ssh_certificate_authority_id", value.sshCertificateAuthorityId.asJson)),
          Option(("public_key", value.publicKey.asJson)),
          value.principals.map(_.asJson).map(("principals", _)),
          value.criticalOptions.map(_.asJson).map(("critical_options", _)),
          value.extensions.map(_.asJson).map(("extensions", _)),
          value.validAfter.map(_.asJson).map(("valid_after", _)),
          value.validUntil.map(_.asJson).map(("valid_until", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class SshUserCertificatesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object SshUserCertificatesUpdate {
    implicit val encodeSshUserCertificatesUpdate: Encoder[SshUserCertificatesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** SSH User Certificates are presented by SSH clients when connecting to an SSH
  *  server to authenticate their connection. The SSH server must trust the SSH
  *  Certificate Authority used to sign the certificate.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates">https://ngrok.com/docs/api#api-ssh-user-certificates</a>.
  */
class SshUserCertificates private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import SshUserCertificates._

  /** Create a new SSH User Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates-create">https://ngrok.com/docs/api#api-ssh-user-certificates-create</a>.
    *
    * @param sshCertificateAuthorityId the ssh certificate authority that is used to sign this ssh user certificate
    * @param publicKey a public key in OpenSSH Authorized Keys format that this certificate signs
    * @param principals the list of principals included in the ssh user certificate. This is the list of usernames that the certificate holder may sign in as on a machine authorizing the signing certificate authority. Dangerously, if no principals are specified, this certificate may be used to log in as any user.
    * @param criticalOptions A map of critical options included in the certificate. Only two critical options are currently defined by OpenSSH: <code>force-command</code> and <code>source-address</code>. See <a href="https://github.com/openssh/openssh-portable/blob/master/PROTOCOL.certkeys">the OpenSSH certificate protocol spec</a> for additional details.
    * @param extensions A map of extensions included in the certificate. Extensions are additional metadata that can be interpreted by the SSH server for any purpose. These can be used to permit or deny the ability to open a terminal, do port forwarding, x11 forwarding, and more. If unspecified, the certificate will include limited permissions with the following extension map: <code>{"permit-pty": "", "permit-user-rc": ""}</code> OpenSSH understands a number of predefined extensions. See <a href="https://github.com/openssh/openssh-portable/blob/master/PROTOCOL.certkeys">the OpenSSH certificate protocol spec</a> for additional details.
    * @param validAfter The time when the user certificate becomes valid, in RFC 3339 format. Defaults to the current time if unspecified.
    * @param validUntil The time when this host certificate becomes invalid, in RFC 3339 format. If unspecified, a default value of 24 hours will be used. The OpenSSH certificates RFC calls this <code>valid_before</code>.
    * @param description human-readable description of this SSH User Certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH User Certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    sshCertificateAuthorityId: String,
    publicKey: String,
    principals: Option[List[String]] = None,
    criticalOptions: Option[Map[String, String]] = None,
    extensions: Option[Map[String, String]] = None,
    validAfter: Option[java.time.OffsetDateTime] = None,
    validUntil: Option[java.time.OffsetDateTime] = None,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[SshUserCertificate] =
    apiClient.sendRequest[SshUserCertificate](
      NgrokApiClient.HttpMethod.Post,
      "/ssh_user_certificates",
      List.empty,
      Option(
        SshUserCertificatesCreate(
          sshCertificateAuthorityId,
          publicKey,
          principals,
          criticalOptions,
          extensions,
          validAfter,
          validUntil,
          description,
          metadata
        ).asJson
      )
    )

  /** Delete an SSH User Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates-delete">https://ngrok.com/docs/api#api-ssh-user-certificates-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ssh_user_certificates/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an SSH User Certficate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates-get">https://ngrok.com/docs/api#api-ssh-user-certificates-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[SshUserCertificate] =
    apiClient.sendRequest[SshUserCertificate](
      NgrokApiClient.HttpMethod.Get,
      s"/ssh_user_certificates/$id",
      List.empty,
      Option.empty
    )

  /** List all SSH User Certificates issued on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates-list">https://ngrok.com/docs/api#api-ssh-user-certificates-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[SshUserCertificateList]] =
    apiClient
      .sendRequest[SshUserCertificateList](
        NgrokApiClient.HttpMethod.Get,
        "/ssh_user_certificates",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update an SSH User Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-user-certificates-update">https://ngrok.com/docs/api#api-ssh-user-certificates-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this SSH User Certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH User Certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[SshUserCertificate] =
    apiClient.sendRequest[SshUserCertificate](
      NgrokApiClient.HttpMethod.Patch,
      s"/ssh_user_certificates/$id",
      List.empty,
      Option(
        SshUserCertificatesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
