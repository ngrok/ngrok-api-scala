package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object SshHostCertificates {
  private case class SshHostCertificatesCreate(
    sshCertificateAuthorityId: String,
    publicKey: String,
    principals: Option[List[String]],
    validAfter: Option[java.time.OffsetDateTime],
    validUntil: Option[java.time.OffsetDateTime],
    description: Option[String],
    metadata: Option[String]
  )

  private object SshHostCertificatesCreate {
    implicit val encodeSshHostCertificatesCreate: Encoder[SshHostCertificatesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          Option(("ssh_certificate_authority_id", value.sshCertificateAuthorityId.asJson)),
          Option(("public_key", value.publicKey.asJson)),
          value.principals.map(_.asJson).map(("principals", _)),
          value.validAfter.map(_.asJson).map(("valid_after", _)),
          value.validUntil.map(_.asJson).map(("valid_until", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class SshHostCertificatesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object SshHostCertificatesUpdate {
    implicit val encodeSshHostCertificatesUpdate: Encoder[SshHostCertificatesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** SSH Host Certificates along with the corresponding private key allows an SSH
  *  server to assert its authenticity to connecting SSH clients who trust the
  *  SSH Certificate Authority that was used to sign the certificate.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates">https://ngrok.com/docs/api#api-ssh-host-certificates</a>.
  */
class SshHostCertificates private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import SshHostCertificates._

  /** Create a new SSH Host Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates-create">https://ngrok.com/docs/api#api-ssh-host-certificates-create</a>.
    *
    * @param sshCertificateAuthorityId the ssh certificate authority that is used to sign this ssh host certificate
    * @param publicKey a public key in OpenSSH Authorized Keys format that this certificate signs
    * @param principals the list of principals included in the ssh host certificate. This is the list of hostnames and/or IP addresses that are authorized to serve SSH traffic with this certificate. Dangerously, if no principals are specified, this certificate is considered valid for all hosts.
    * @param validAfter The time when the host certificate becomes valid, in RFC 3339 format. Defaults to the current time if unspecified.
    * @param validUntil The time when this host certificate becomes invalid, in RFC 3339 format. If unspecified, a default value of one year in the future will be used. The OpenSSH certificates RFC calls this <code>valid_before</code>.
    * @param description human-readable description of this SSH Host Certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH Host Certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    sshCertificateAuthorityId: String,
    publicKey: String,
    principals: Option[List[String]] = None,
    validAfter: Option[java.time.OffsetDateTime] = None,
    validUntil: Option[java.time.OffsetDateTime] = None,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[SshHostCertificate] =
    apiClient.sendRequest[SshHostCertificate](
      NgrokApiClient.HttpMethod.Post,
      "/ssh_host_certificates",
      List.empty,
      Option(
        SshHostCertificatesCreate(
          sshCertificateAuthorityId,
          publicKey,
          principals,
          validAfter,
          validUntil,
          description,
          metadata
        ).asJson
      )
    )

  /** Delete an SSH Host Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates-delete">https://ngrok.com/docs/api#api-ssh-host-certificates-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ssh_host_certificates/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an SSH Host Certficate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates-get">https://ngrok.com/docs/api#api-ssh-host-certificates-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[SshHostCertificate] =
    apiClient.sendRequest[SshHostCertificate](
      NgrokApiClient.HttpMethod.Get,
      s"/ssh_host_certificates/$id",
      List.empty,
      Option.empty
    )

  /** List all SSH Host Certificates issued on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates-list">https://ngrok.com/docs/api#api-ssh-host-certificates-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[SshHostCertificateList]] =
    apiClient
      .sendRequest[SshHostCertificateList](
        NgrokApiClient.HttpMethod.Get,
        "/ssh_host_certificates",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update an SSH Host Certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-host-certificates-update">https://ngrok.com/docs/api#api-ssh-host-certificates-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this SSH Host Certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH Host Certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[SshHostCertificate] =
    apiClient.sendRequest[SshHostCertificate](
      NgrokApiClient.HttpMethod.Patch,
      s"/ssh_host_certificates/$id",
      List.empty,
      Option(
        SshHostCertificatesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
