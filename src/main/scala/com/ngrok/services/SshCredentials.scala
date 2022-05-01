package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object SshCredentials {
  private case class SshCredentialsCreate(
    description: Option[String],
    metadata: Option[String],
    acl: List[String],
    publicKey: String
  )

  private object SshCredentialsCreate {
    implicit val encodeSshCredentialsCreate: Encoder[SshCredentialsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.acl.isEmpty) None else Option(("acl", value.acl.asJson)),
        Option(("public_key", value.publicKey.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class SshCredentialsUpdate(
    description: Option[String],
    metadata: Option[String],
    acl: List[String]
  )

  private object SshCredentialsUpdate {
    implicit val encodeSshCredentialsUpdate: Encoder[SshCredentialsUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        if (value.acl.isEmpty) None else Option(("acl", value.acl.asJson))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** SSH Credentials are SSH public keys that can be used to start SSH tunnels
  *  via the ngrok SSH tunnel gateway.
  *
  * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials">https://ngrok.com/docs/api#api-ssh-credentials</a>.
  */
class SshCredentials private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import SshCredentials._

  /** Create a new ssh_credential from an uploaded public SSH key. This ssh credential
    * can be used to start new tunnels via ngrok&#39;s SSH gateway.
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials-create">https://ngrok.com/docs/api#api-ssh-credentials-create</a>.
    *
    * @param publicKey the PEM-encoded public key of the SSH keypair that will be used to authenticate
    * @param description human-readable description of who or what will use the ssh credential to authenticate. Optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this ssh credential. Optional, max 4096 bytes.
    * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>&#39;*&#39;</code> is equivalent to no acl at all and will explicitly permit all actions.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    publicKey: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    acl: List[String] = List.empty
  ): Future[SshCredential] =
    apiClient.sendRequest[SshCredential](
      NgrokApiClient.HttpMethod.Post,
      "/ssh_credentials",
      List.empty,
      Option(
        SshCredentialsCreate(
          description,
          metadata,
          acl,
          publicKey
        ).asJson
      )
    )

  /** Delete an ssh_credential by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials-delete">https://ngrok.com/docs/api#api-ssh-credentials-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/ssh_credentials/$id",
      List.empty,
      Option.empty
    )

  /** Get detailed information about an ssh_credential
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials-get">https://ngrok.com/docs/api#api-ssh-credentials-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[SshCredential] =
    apiClient.sendRequest[SshCredential](
      NgrokApiClient.HttpMethod.Get,
      s"/ssh_credentials/$id",
      List.empty,
      Option.empty
    )

  /** List all ssh credentials on this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials-list">https://ngrok.com/docs/api#api-ssh-credentials-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[SshCredentialList]] =
    apiClient
      .sendRequest[SshCredentialList](
        NgrokApiClient.HttpMethod.Get,
        "/ssh_credentials",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an ssh_credential by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-ssh-credentials-update">https://ngrok.com/docs/api#api-ssh-credentials-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of who or what will use the ssh credential to authenticate. Optional, max 255 bytes.
    * @param metadata arbitrary user-defined machine-readable data of this ssh credential. Optional, max 4096 bytes.
    * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>&#39;*&#39;</code> is equivalent to no acl at all and will explicitly permit all actions.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None,
    acl: List[String] = List.empty
  ): Future[SshCredential] =
    apiClient.sendRequest[SshCredential](
      NgrokApiClient.HttpMethod.Patch,
      s"/ssh_credentials/$id",
      List.empty,
      Option(
        SshCredentialsUpdate(
          description,
          metadata,
          acl
        ).asJson
      )
    )

}
