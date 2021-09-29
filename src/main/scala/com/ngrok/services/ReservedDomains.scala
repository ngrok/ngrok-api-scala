package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object ReservedDomains {
  private case class ReservedDomainsCreate(
    name: String,
    region: Option[String],
    description: Option[String],
    metadata: Option[String],
    httpEndpointConfigurationId: Option[String],
    httpsEndpointConfigurationId: Option[String],
    certificateId: Option[String],
    certificateManagementPolicy: Option[ReservedDomainCertPolicy]
  )

  private object ReservedDomainsCreate {
    implicit val encodeReservedDomainsCreate: Encoder[ReservedDomainsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          Option(("name", value.name.asJson)),
          value.region.map(_.asJson).map(("region", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.httpEndpointConfigurationId.map(_.asJson).map(("http_endpoint_configuration_id", _)),
          value.httpsEndpointConfigurationId.map(_.asJson).map(("https_endpoint_configuration_id", _)),
          value.certificateId.map(_.asJson).map(("certificate_id", _)),
          value.certificateManagementPolicy.map(_.asJson).map(("certificate_management_policy", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class ReservedDomainsUpdate(
    description: Option[String],
    metadata: Option[String],
    httpEndpointConfigurationId: Option[String],
    httpsEndpointConfigurationId: Option[String],
    certificateId: Option[String],
    certificateManagementPolicy: Option[ReservedDomainCertPolicy]
  )

  private object ReservedDomainsUpdate {
    implicit val encodeReservedDomainsUpdate: Encoder[ReservedDomainsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.httpEndpointConfigurationId.map(_.asJson).map(("http_endpoint_configuration_id", _)),
          value.httpsEndpointConfigurationId.map(_.asJson).map(("https_endpoint_configuration_id", _)),
          value.certificateId.map(_.asJson).map(("certificate_id", _)),
          value.certificateManagementPolicy.map(_.asJson).map(("certificate_management_policy", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** Reserved Domains are hostnames that you can listen for traffic on. Domains
  *  can be used to listen for http, https or tls traffic. You may use a domain
  *  that you own by creating a CNAME record specified in the returned resource.
  *  This CNAME record points traffic for that domain to ngrok's edge servers.
  *
  * See also <a href="https://ngrok.com/docs/api#api-reserved-domains">https://ngrok.com/docs/api#api-reserved-domains</a>.
  */
class ReservedDomains private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import ReservedDomains._

  /** Create a new reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-create">https://ngrok.com/docs/api#api-reserved-domains-create</a>.
    *
    * @param name the domain name to reserve. It may be a full domain name like app.example.com. If the name does not contain a '.' it will reserve that subdomain on ngrok.io.
    * @param region reserve the domain in this geographic ngrok datacenter. Optional, default is us. (au, eu, ap, us, jp, in, sa)
    * @param description human-readable description of what this reserved domain will be used for
    * @param metadata arbitrary user-defined machine-readable data of this reserved domain. Optional, max 4096 bytes.
    * @param httpEndpointConfigurationId ID of an endpoint configuration of type http that will be used to handle inbound http traffic to this domain
    * @param httpsEndpointConfigurationId ID of an endpoint configuration of type https that will be used to handle inbound https traffic to this domain
    * @param certificateId ID of a user-uploaded TLS certificate to use for connections to targeting this domain. Optional, mutually exclusive with <code>certificate_management_policy</code>.
    * @param certificateManagementPolicy configuration for automatic management of TLS certificates for this domain, or null if automatic management is disabled. Optional, mutually exclusive with <code>certificate_id</code>.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    name: String,
    region: Option[String] = None,
    description: Option[String] = None,
    metadata: Option[String] = None,
    httpEndpointConfigurationId: Option[String] = None,
    httpsEndpointConfigurationId: Option[String] = None,
    certificateId: Option[String] = None,
    certificateManagementPolicy: Option[ReservedDomainCertPolicy] = None
  ): Future[ReservedDomain] =
    apiClient.sendRequest[ReservedDomain](
      NgrokApiClient.HttpMethod.Post,
      "/reserved_domains",
      List.empty,
      Option(
        ReservedDomainsCreate(
          name,
          region,
          description,
          metadata,
          httpEndpointConfigurationId,
          httpsEndpointConfigurationId,
          certificateId,
          certificateManagementPolicy
        ).asJson
      )
    )

  /** Delete a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-delete">https://ngrok.com/docs/api#api-reserved-domains-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_domains/$id",
      List.empty,
      Option.empty
    )

  /** Get the details of a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-get">https://ngrok.com/docs/api#api-reserved-domains-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[ReservedDomain] =
    apiClient.sendRequest[ReservedDomain](
      NgrokApiClient.HttpMethod.Get,
      s"/reserved_domains/$id",
      List.empty,
      Option.empty
    )

  /** List all reserved domains on this account.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-list">https://ngrok.com/docs/api#api-reserved-domains-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[ReservedDomainList]] =
    apiClient
      .sendRequest[ReservedDomainList](
        NgrokApiClient.HttpMethod.Get,
        "/reserved_domains",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update the attributes of a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-update">https://ngrok.com/docs/api#api-reserved-domains-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of what this reserved domain will be used for
    * @param metadata arbitrary user-defined machine-readable data of this reserved domain. Optional, max 4096 bytes.
    * @param httpEndpointConfigurationId ID of an endpoint configuration of type http that will be used to handle inbound http traffic to this domain
    * @param httpsEndpointConfigurationId ID of an endpoint configuration of type https that will be used to handle inbound https traffic to this domain
    * @param certificateId ID of a user-uploaded TLS certificate to use for connections to targeting this domain. Optional, mutually exclusive with <code>certificate_management_policy</code>.
    * @param certificateManagementPolicy configuration for automatic management of TLS certificates for this domain, or null if automatic management is disabled. Optional, mutually exclusive with <code>certificate_id</code>.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    httpEndpointConfigurationId: Option[String] = None,
    httpsEndpointConfigurationId: Option[String] = None,
    certificateId: Option[String] = None,
    certificateManagementPolicy: Option[ReservedDomainCertPolicy] = None
  ): Future[ReservedDomain] =
    apiClient.sendRequest[ReservedDomain](
      NgrokApiClient.HttpMethod.Patch,
      s"/reserved_domains/$id",
      List.empty,
      Option(
        ReservedDomainsUpdate(
          description,
          metadata,
          httpEndpointConfigurationId,
          httpsEndpointConfigurationId,
          certificateId,
          certificateManagementPolicy
        ).asJson
      )
    )

  /** Detach the certificate management policy attached to a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-delete-certificate-management-policy">https://ngrok.com/docs/api#api-reserved-domains-delete-certificate-management-policy</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def deleteCertificateManagementPolicy(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_domains/$id/certificate_management_policy",
      List.empty,
      Option.empty
    )

  /** Detach the certificate attached to a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-delete-certificate">https://ngrok.com/docs/api#api-reserved-domains-delete-certificate</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def deleteCertificate(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_domains/$id/certificate",
      List.empty,
      Option.empty
    )

  /** Detach the http endpoint configuration attached to a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-delete-http-endpoint-config">https://ngrok.com/docs/api#api-reserved-domains-delete-http-endpoint-config</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def deleteHttpEndpointConfig(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_domains/$id/http_endpoint_configuration",
      List.empty,
      Option.empty
    )

  /** Detach the https endpoint configuration attached to a reserved domain.
    *
    * See also <a href="https://ngrok.com/docs/api#api-reserved-domains-delete-https-endpoint-config">https://ngrok.com/docs/api#api-reserved-domains-delete-https-endpoint-config</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def deleteHttpsEndpointConfig(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/reserved_domains/$id/https_endpoint_configuration",
      List.empty,
      Option.empty
    )

}
