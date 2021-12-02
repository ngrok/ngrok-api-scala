package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object AgentIngresses {
  private case class AgentIngressesCreate(
    description: Option[String],
    metadata: Option[String],
    domain: String
  )

  private object AgentIngressesCreate {
    implicit val encodeAgentIngressesCreate: Encoder[AgentIngressesCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        Option(("domain", value.domain.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class AgentIngressesUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object AgentIngressesUpdate {
    implicit val encodeAgentIngressesUpdate: Encoder[AgentIngressesUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** An API client for [[AgentIngresses]].
  *
  * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses">https://ngrok.com/docs/api#api-agent-ingresses</a>.
  */
class AgentIngresses private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import AgentIngresses._

  /** Create a new Agent Ingress. The ngrok agent can be configured to connect to
    * ngrok via the new set of addresses on the returned Agent Ingress.
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses-create">https://ngrok.com/docs/api#api-agent-ingresses-create</a>.
    *
    * @param domain the domain that you own to be used as the base domain name to generate regional agent ingress domains.
    * @param description human-readable description of the use of this Agent Ingress. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this Agent Ingress. optional, max 4096 bytes
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    domain: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[AgentIngress] =
    apiClient.sendRequest[AgentIngress](
      NgrokApiClient.HttpMethod.Post,
      "/agent_ingresses",
      List.empty,
      Option(
        AgentIngressesCreate(
          description,
          metadata,
          domain
        ).asJson
      )
    )

  /** Delete an Agent Ingress by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses-delete">https://ngrok.com/docs/api#api-agent-ingresses-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/agent_ingresses/$id",
      List.empty,
      Option.empty
    )

  /** Get the details of an Agent Ingress by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses-get">https://ngrok.com/docs/api#api-agent-ingresses-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[AgentIngress] =
    apiClient.sendRequest[AgentIngress](
      NgrokApiClient.HttpMethod.Get,
      s"/agent_ingresses/$id",
      List.empty,
      Option.empty
    )

  /** List all Agent Ingresses owned by this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses-list">https://ngrok.com/docs/api#api-agent-ingresses-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[AgentIngressList]] =
    apiClient
      .sendRequest[AgentIngressList](
        NgrokApiClient.HttpMethod.Get,
        "/agent_ingresses",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an Agent Ingress by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-agent-ingresses-update">https://ngrok.com/docs/api#api-agent-ingresses-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of the use of this Agent Ingress. optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this Agent Ingress. optional, max 4096 bytes
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[AgentIngress] =
    apiClient.sendRequest[AgentIngress](
      NgrokApiClient.HttpMethod.Patch,
      s"/agent_ingresses/$id",
      List.empty,
      Option(
        AgentIngressesUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
