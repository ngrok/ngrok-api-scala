/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object CertificateAuthorities {
  private case class CertificateAuthoritiesCreate(
    description: Option[String],
    metadata: Option[String],
    caPem: String
  )

  private object CertificateAuthoritiesCreate {
    implicit val encodeCertificateAuthoritiesCreate: Encoder[CertificateAuthoritiesCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          Option(("ca_pem", value.caPem.asJson))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class CertificateAuthoritiesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object CertificateAuthoritiesUpdate {
    implicit val encodeCertificateAuthoritiesUpdate: Encoder[CertificateAuthoritiesUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** Certificate Authorities are x509 certificates that are used to sign other
  *  x509 certificates. Attach a Certificate Authority to the Mutual TLS module
  *  to verify that the TLS certificate presented by a client has been signed by
  *  this CA. Certificate Authorities  are used only for mTLS validation only and
  *  thus a private key is not included in the resource.
  *
  * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities">https://ngrok.com/docs/api#api-certificate-authorities</a>.
  */
class CertificateAuthorities private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import CertificateAuthorities._

  /** Upload a new Certificate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities-create">https://ngrok.com/docs/api#api-certificate-authorities-create</a>.
    *
    * @param caPem raw PEM of the Certificate Authority
    * @param description human-readable description of this Certificate Authority. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this Certificate Authority. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    caPem: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[CertificateAuthority] =
    apiClient.sendRequest[CertificateAuthority](
      NgrokApiClient.HttpMethod.Post,
      "/certificate_authorities",
      List.empty,
      Option(
        CertificateAuthoritiesCreate(
          description,
          metadata,
          caPem
        ).asJson
      )
    )

  /** Delete a Certificate Authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities-delete">https://ngrok.com/docs/api#api-certificate-authorities-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/certificate_authorities/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a certficate authority
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities-get">https://ngrok.com/docs/api#api-certificate-authorities-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[CertificateAuthority] =
    apiClient.sendRequest[CertificateAuthority](
      NgrokApiClient.HttpMethod.Get,
      s"/certificate_authorities/$id",
      List.empty,
      Option.empty
    )

  /** List all Certificate Authority on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities-list">https://ngrok.com/docs/api#api-certificate-authorities-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[CertificateAuthorityList]] =
    apiClient
      .sendRequest[CertificateAuthorityList](
        NgrokApiClient.HttpMethod.Get,
        "/certificate_authorities",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of a Certificate Authority by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-certificate-authorities-update">https://ngrok.com/docs/api#api-certificate-authorities-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of this Certificate Authority. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this Certificate Authority. optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[CertificateAuthority] =
    apiClient.sendRequest[CertificateAuthority](
      NgrokApiClient.HttpMethod.Patch,
      s"/certificate_authorities/$id",
      List.empty,
      Option(
        CertificateAuthoritiesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
