/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object TlsCertificates {
  private case class TlsCertificatesCreate(
    description: Option[String],
    metadata: Option[String],
    certificatePem: String,
    privateKeyPem: String
  )

  private object TlsCertificatesCreate {
    implicit val encodeTlsCertificatesCreate: Encoder[TlsCertificatesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          Option(("certificate_pem", value.certificatePem.asJson)),
          Option(("private_key_pem", value.privateKeyPem.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class TlsCertificatesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object TlsCertificatesUpdate {
    implicit val encodeTlsCertificatesUpdate: Encoder[TlsCertificatesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** TLS Certificates are pairs of x509 certificates and their matching private
  *  key that can be used to terminate TLS traffic. TLS certificates are unused
  *  until they are attached to a Domain. TLS Certificates may also be
  *  provisioned by ngrok automatically for domains on which you have enabled
  *  automated certificate provisioning.
  *
  * See also <a href="https://ngrok.com/docs/api#api-tls-certificates">https://ngrok.com/docs/api#api-tls-certificates</a>.
  */
class TlsCertificates private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import TlsCertificates._

  /** Upload a new TLS certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates-create">https://ngrok.com/docs/api#api-tls-certificates-create</a>.
    *
    * @param certificatePem chain of PEM-encoded certificates, leaf first. See <a href="/cloud-edge/endpoints#certificate-chains">Certificate Bundles</a>.
    * @param privateKeyPem private key for the TLS certificate, PEM-encoded. See <a href="/cloud-edge/endpoints#private-keys">Private Keys</a>.
    * @param description human-readable description of this TLS certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this TLS certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    certificatePem: String,
    privateKeyPem: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[TlsCertificate] =
    apiClient.sendRequest[TlsCertificate](
      NgrokApiClient.HttpMethod.Post,
      "/tls_certificates",
      List.empty,
      Option(
        TlsCertificatesCreate(
          description,
          metadata,
          certificatePem,
          privateKeyPem
        ).asJson
      )
    )

  /** Delete a TLS certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates-delete">https://ngrok.com/docs/api#api-tls-certificates-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/tls_certificates/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a TLS certificate
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates-get">https://ngrok.com/docs/api#api-tls-certificates-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[TlsCertificate] =
    apiClient.sendRequest[TlsCertificate](
      NgrokApiClient.HttpMethod.Get,
      s"/tls_certificates/$id",
      List.empty,
      Option.empty
    )

  /** List all TLS certificates on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates-list">https://ngrok.com/docs/api#api-tls-certificates-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[TlsCertificateList]] =
    apiClient
      .sendRequest[TlsCertificateList](
        NgrokApiClient.HttpMethod.Get,
        "/tls_certificates",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of a TLS Certificate by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-tls-certificates-update">https://ngrok.com/docs/api#api-tls-certificates-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this TLS certificate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this TLS certificate. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[TlsCertificate] =
    apiClient.sendRequest[TlsCertificate](
      NgrokApiClient.HttpMethod.Patch,
      s"/tls_certificates/$id",
      List.empty,
      Option(
        TlsCertificatesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
