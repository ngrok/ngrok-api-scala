package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object SshCertificateAuthorities {
  private case class SshCertificateAuthoritiesCreate(
    description: Option[String],
    metadata: Option[String],
    privateKeyType: Option[String],
    ellipticCurve: Option[String],
    keySize: Option[Long]
  )

  private object SshCertificateAuthoritiesCreate {
    implicit val encodeSshCertificateAuthoritiesCreate: Encoder[SshCertificateAuthoritiesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.privateKeyType.map(_.asJson).map(("private_key_type", _)),
          value.ellipticCurve.map(_.asJson).map(("elliptic_curve", _)),
          value.keySize.map(_.asJson).map(("key_size", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class SshCertificateAuthoritiesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object SshCertificateAuthoritiesUpdate {
    implicit val encodeSshCertificateAuthoritiesUpdate: Encoder[SshCertificateAuthoritiesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** An SSH Certificate Authority is a pair of an SSH Certificate and its private
  *  key that can be used to sign other SSH host and user certificates.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities">https://ngrok.com/docs/api#api-ssh-certificate-authorities</a>.
  */
class SshCertificateAuthorities private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import SshCertificateAuthorities._

  /** Create a new SSH Certificate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities-create">https://ngrok.com/docs/api#api-ssh-certificate-authorities-create</a>.
    *
    * @param description human-readable description of this SSH Certificate Authority. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH Certificate Authority. optional, max 4096 bytes.
    * @param privateKeyType the type of private key to generate. one of <code>rsa</code>, <code>ecdsa</code>, <code>ed25519</code>
    * @param ellipticCurve the type of elliptic curve to use when creating an ECDSA key
    * @param keySize the key size to use when creating an RSA key. one of <code>2048</code> or <code>4096</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    privateKeyType: Option[String] = None,
    ellipticCurve: Option[String] = None,
    keySize: Option[Long] = None
  ): Future[SshCertificateAuthority] =
    apiClient.sendRequest[SshCertificateAuthority](
      NgrokApiClient.HttpMethod.Post,
      "/ssh_certificate_authorities",
      List.empty,
      Option(
        SshCertificateAuthoritiesCreate(
          description,
          metadata,
          privateKeyType,
          ellipticCurve,
          keySize
        ).asJson
      )
    )

  /** Delete an SSH Certificate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities-delete">https://ngrok.com/docs/api#api-ssh-certificate-authorities-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ssh_certificate_authorities/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an SSH Certficate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities-get">https://ngrok.com/docs/api#api-ssh-certificate-authorities-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[SshCertificateAuthority] =
    apiClient.sendRequest[SshCertificateAuthority](
      NgrokApiClient.HttpMethod.Get,
      s"/ssh_certificate_authorities/$id",
      List.empty,
      Option.empty
    )

  /** List all SSH Certificate Authorities on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities-list">https://ngrok.com/docs/api#api-ssh-certificate-authorities-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[SshCertificateAuthorityList]] =
    apiClient
      .sendRequest[SshCertificateAuthorityList](
        NgrokApiClient.HttpMethod.Get,
        "/ssh_certificate_authorities",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update an SSH Certificate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-certificate-authorities-update">https://ngrok.com/docs/api#api-ssh-certificate-authorities-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this SSH Certificate Authority. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this SSH Certificate Authority. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[SshCertificateAuthority] =
    apiClient.sendRequest[SshCertificateAuthority](
      NgrokApiClient.HttpMethod.Patch,
      s"/ssh_certificate_authorities/$id",
      List.empty,
      Option(
        SshCertificateAuthoritiesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
