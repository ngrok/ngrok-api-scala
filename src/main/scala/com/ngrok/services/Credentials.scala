package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object Credentials {
  private case class CredentialsCreate(
    description: Option[String],
    metadata: Option[String],
    acl: Option[List[String]]
  )

  private object CredentialsCreate {
    implicit val encodeCredentialsCreate: Encoder[CredentialsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.acl.map(_.asJson).map(("acl", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class CredentialsUpdate(
    description: Option[String],
    metadata: Option[String],
    acl: Option[List[String]]
  )

  private object CredentialsUpdate {
    implicit val encodeCredentialsUpdate: Encoder[CredentialsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.acl.map(_.asJson).map(("acl", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** Tunnel Credentials are ngrok agent authtokens. They authorize the ngrok
  *  agent to connect the ngrok service as your account. They are installed with
  *  the <code>ngrok authtoken</code> command or by specifying it in the
  * <code>ngrok.yml</code>
  *  configuration file with the <code>authtoken</code> property.
  *
  * See also <a href="https://ngrok.com/docs/api#api-credentials">https://ngrok.com/docs/api#api-credentials</a>.
  */
class Credentials private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import Credentials._

  /** Create a new tunnel authtoken credential. This authtoken credential can be used
    * to start a new tunnel session. The response to this API call is the only time
    * the generated token is available. If you need it for future use, you must save
    * it securely yourself.
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials-create">https://ngrok.com/docs/api#api-credentials-create</a>.
    *
    * @param description human-readable description of who or what will use the credential to authenticate. Optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this credential. Optional, max 4096 bytes.
    * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>'*'</code> is equivalent to no acl at all and will explicitly permit all actions.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    acl: Option[List[String]] = None
  ): Future[Credential] =
    apiClient.sendRequest[Credential](
      NgrokApiClient.HttpMethod.Post,
      "/credentials",
      List.empty,
      Option(
        CredentialsCreate(
          description,
          metadata,
          acl
        ).asJson
      )
    )

  /** Delete a tunnel authtoken credential by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials-delete">https://ngrok.com/docs/api#api-credentials-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/credentials/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about a tunnel authtoken credential
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials-get">https://ngrok.com/docs/api#api-credentials-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[Credential] =
    apiClient.sendRequest[Credential](
      NgrokApiClient.HttpMethod.Get,
      s"/credentials/$id",
      List.empty,
      Option.empty
    )

  /** List all tunnel authtoken credentials on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials-list">https://ngrok.com/docs/api#api-credentials-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[CredentialList]] =
    apiClient
      .sendRequest[CredentialList](
        NgrokApiClient.HttpMethod.Get,
        "/credentials",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an tunnel authtoken credential by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-credentials-update">https://ngrok.com/docs/api#api-credentials-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of who or what will use the credential to authenticate. Optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this credential. Optional, max 4096 bytes.
    * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>'*'</code> is equivalent to no acl at all and will explicitly permit all actions.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    acl: Option[List[String]] = None
  ): Future[Credential] =
    apiClient.sendRequest[Credential](
      NgrokApiClient.HttpMethod.Patch,
      s"/credentials/$id",
      List.empty,
      Option(
        CredentialsUpdate(
          description,
          metadata,
          acl
        ).asJson
      )
    )

}
