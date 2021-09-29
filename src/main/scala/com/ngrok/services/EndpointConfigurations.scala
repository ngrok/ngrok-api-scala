package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object EndpointConfigurations {
  private case class EndpointConfigurationsCreate(
    `type`: Option[String],
    description: Option[String],
    metadata: Option[String],
    circuitBreaker: Option[EndpointCircuitBreaker],
    compression: Option[EndpointCompression],
    requestHeaders: Option[EndpointRequestHeaders],
    responseHeaders: Option[EndpointResponseHeaders],
    ipPolicy: Option[EndpointIpPolicyMutate],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTermination],
    webhookValidation: Option[EndpointWebhookValidation],
    oauth: Option[EndpointOAuth],
    logging: Option[EndpointLoggingMutate],
    saml: Option[EndpointSamlMutate],
    oidc: Option[EndpointOidc]
  )

  private object EndpointConfigurationsCreate {
    implicit val encodeEndpointConfigurationsCreate: Encoder[EndpointConfigurationsCreate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.`type`.map(_.asJson).map(("type", _)),
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.circuitBreaker.map(_.asJson).map(("circuit_breaker", _)),
          value.compression.map(_.asJson).map(("compression", _)),
          value.requestHeaders.map(_.asJson).map(("request_headers", _)),
          value.responseHeaders.map(_.asJson).map(("response_headers", _)),
          value.ipPolicy.map(_.asJson).map(("ip_policy", _)),
          value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
          value.tlsTermination.map(_.asJson).map(("tls_termination", _)),
          value.webhookValidation.map(_.asJson).map(("webhook_validation", _)),
          value.oauth.map(_.asJson).map(("oauth", _)),
          value.logging.map(_.asJson).map(("logging", _)),
          value.saml.map(_.asJson).map(("saml", _)),
          value.oidc.map(_.asJson).map(("oidc", _))
        ).flatten.toMap.asJsonObject
      )
  }

  private case class EndpointConfigurationsUpdate(
    description: Option[String],
    metadata: Option[String],
    circuitBreaker: Option[EndpointCircuitBreaker],
    compression: Option[EndpointCompression],
    requestHeaders: Option[EndpointRequestHeaders],
    responseHeaders: Option[EndpointResponseHeaders],
    ipPolicy: Option[EndpointIpPolicyMutate],
    mutualTls: Option[EndpointMutualTlsMutate],
    tlsTermination: Option[EndpointTlsTermination],
    webhookValidation: Option[EndpointWebhookValidation],
    oauth: Option[EndpointOAuth],
    logging: Option[EndpointLoggingMutate],
    saml: Option[EndpointSamlMutate],
    oidc: Option[EndpointOidc]
  )

  private object EndpointConfigurationsUpdate {
    implicit val encodeEndpointConfigurationsUpdate: Encoder[EndpointConfigurationsUpdate] =
      Encoder.encodeJsonObject.contramap(value =>
        List(
          value.description.map(_.asJson).map(("description", _)),
          value.metadata.map(_.asJson).map(("metadata", _)),
          value.circuitBreaker.map(_.asJson).map(("circuit_breaker", _)),
          value.compression.map(_.asJson).map(("compression", _)),
          value.requestHeaders.map(_.asJson).map(("request_headers", _)),
          value.responseHeaders.map(_.asJson).map(("response_headers", _)),
          value.ipPolicy.map(_.asJson).map(("ip_policy", _)),
          value.mutualTls.map(_.asJson).map(("mutual_tls", _)),
          value.tlsTermination.map(_.asJson).map(("tls_termination", _)),
          value.webhookValidation.map(_.asJson).map(("webhook_validation", _)),
          value.oauth.map(_.asJson).map(("oauth", _)),
          value.logging.map(_.asJson).map(("logging", _)),
          value.saml.map(_.asJson).map(("saml", _)),
          value.oidc.map(_.asJson).map(("oidc", _))
        ).flatten.toMap.asJsonObject
      )
  }

}

/** Endpoint Configurations are a reusable group of modules that encapsulate how
  *  traffic to a domain or address is handled. Endpoint configurations are only
  *  applied to Domains and TCP Addresses they have been attached to.
  *
  * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations">https://ngrok.com/docs/api#api-endpoint-configurations</a>.
  */
class EndpointConfigurations private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import EndpointConfigurations._

  /** Create a new endpoint configuration
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations-create">https://ngrok.com/docs/api#api-endpoint-configurations-create</a>.
    *
    * @param `type` they type of traffic this endpoint configuration can be applied to. one of: <code>http</code>, <code>https</code>, <code>tcp</code>
    * @param description human-readable description of what this endpoint configuration will be do when applied or what traffic it will be applied to. Optional, max 255 bytes
    * @param metadata arbitrary user-defined machine-readable data of this endpoint configuration. Optional, max 4096 bytes.
    * @param circuitBreaker circuit breaker module configuration or <code>null</code>
    * @param compression compression module configuration or <code>null</code>
    * @param requestHeaders request headers module configuration or <code>null</code>
    * @param responseHeaders response headers module configuration or <code>null</code>
    * @param ipPolicy ip policy module configuration or <code>null</code>
    * @param mutualTls mutual TLS module configuration or <code>null</code>
    * @param tlsTermination TLS termination module configuration or <code>null</code>
    * @param webhookValidation webhook validation module configuration or <code>null</code>
    * @param oauth oauth module configuration or <code>null</code>
    * @param logging logging module configuration or <code>null</code>
    * @param saml saml module configuration or <code>null</code>
    * @param oidc oidc module configuration or <code>null</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    `type`: Option[String] = None,
    description: Option[String] = None,
    metadata: Option[String] = None,
    circuitBreaker: Option[EndpointCircuitBreaker] = None,
    compression: Option[EndpointCompression] = None,
    requestHeaders: Option[EndpointRequestHeaders] = None,
    responseHeaders: Option[EndpointResponseHeaders] = None,
    ipPolicy: Option[EndpointIpPolicyMutate] = None,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTermination] = None,
    webhookValidation: Option[EndpointWebhookValidation] = None,
    oauth: Option[EndpointOAuth] = None,
    logging: Option[EndpointLoggingMutate] = None,
    saml: Option[EndpointSamlMutate] = None,
    oidc: Option[EndpointOidc] = None
  ): Future[EndpointConfiguration] =
    apiClient.sendRequest[EndpointConfiguration](
      NgrokApiClient.HttpMethod.Post,
      "/endpoint_configurations",
      List.empty,
      Option(
        EndpointConfigurationsCreate(
          `type`,
          description,
          metadata,
          circuitBreaker,
          compression,
          requestHeaders,
          responseHeaders,
          ipPolicy,
          mutualTls,
          tlsTermination,
          webhookValidation,
          oauth,
          logging,
          saml,
          oidc
        ).asJson
      )
    )

  /** Delete an endpoint configuration. This operation will fail if the endpoint
    * configuration is still referenced by any reserved domain or reserved address.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations-delete">https://ngrok.com/docs/api#api-endpoint-configurations-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/endpoint_configurations/$id",
      List.empty,
      Option.empty
    )

  /** Returns detailed information about an endpoint configuration
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations-get">https://ngrok.com/docs/api#api-endpoint-configurations-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[EndpointConfiguration] =
    apiClient.sendRequest[EndpointConfiguration](
      NgrokApiClient.HttpMethod.Get,
      s"/endpoint_configurations/$id",
      List.empty,
      Option.empty
    )

  /** Returns a list of all endpoint configurations on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations-list">https://ngrok.com/docs/api#api-endpoint-configurations-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[EndpointConfigurationList]] =
    apiClient
      .sendRequest[EndpointConfigurationList](
        NgrokApiClient.HttpMethod.Get,
        "/endpoint_configurations",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Updates an endpoint configuration. If a module is not specified in the update,
    * it will not be modified. However, each module configuration that is specified
    * will completely replace the existing value. There is no way to delete an
    * existing module via this API, instead use the delete module API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-endpoint-configurations-update">https://ngrok.com/docs/api#api-endpoint-configurations-update</a>.
    *
    * @param id unique identifier of this endpoint configuration
    * @param description human-readable description of what this endpoint configuration will be do when applied or what traffic it will be applied to. Optional, max 255 bytes
    * @param metadata arbitrary user-defined machine-readable data of this endpoint configuration. Optional, max 4096 bytes.
    * @param circuitBreaker circuit breaker module configuration or <code>null</code>
    * @param compression compression module configuration or <code>null</code>
    * @param requestHeaders request headers module configuration or <code>null</code>
    * @param responseHeaders response headers module configuration or <code>null</code>
    * @param ipPolicy ip policy module configuration or <code>null</code>
    * @param mutualTls mutual TLS module configuration or <code>null</code>
    * @param tlsTermination TLS termination module configuration or <code>null</code>
    * @param webhookValidation webhook validation module configuration or <code>null</code>
    * @param oauth oauth module configuration or <code>null</code>
    * @param logging logging module configuration or <code>null</code>
    * @param saml saml module configuration or <code>null</code>
    * @param oidc oidc module configuration or <code>null</code>
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    circuitBreaker: Option[EndpointCircuitBreaker] = None,
    compression: Option[EndpointCompression] = None,
    requestHeaders: Option[EndpointRequestHeaders] = None,
    responseHeaders: Option[EndpointResponseHeaders] = None,
    ipPolicy: Option[EndpointIpPolicyMutate] = None,
    mutualTls: Option[EndpointMutualTlsMutate] = None,
    tlsTermination: Option[EndpointTlsTermination] = None,
    webhookValidation: Option[EndpointWebhookValidation] = None,
    oauth: Option[EndpointOAuth] = None,
    logging: Option[EndpointLoggingMutate] = None,
    saml: Option[EndpointSamlMutate] = None,
    oidc: Option[EndpointOidc] = None
  ): Future[EndpointConfiguration] =
    apiClient.sendRequest[EndpointConfiguration](
      NgrokApiClient.HttpMethod.Patch,
      s"/endpoint_configurations/$id",
      List.empty,
      Option(
        EndpointConfigurationsUpdate(
          description,
          metadata,
          circuitBreaker,
          compression,
          requestHeaders,
          responseHeaders,
          ipPolicy,
          mutualTls,
          tlsTermination,
          webhookValidation,
          oauth,
          logging,
          saml,
          oidc
        ).asJson
      )
    )

}
