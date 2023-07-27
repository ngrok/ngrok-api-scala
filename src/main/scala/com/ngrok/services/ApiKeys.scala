/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object ApiKeys {
  private case class ApiKeysCreate(
    description: Option[String],
    metadata: Option[String],
    ownerId: Option[String]
  )

  private object ApiKeysCreate {
    implicit val encodeApiKeysCreate: Encoder[ApiKeysCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _)),
        value.ownerId.map(_.asJson).map(("owner_id", _))
      ).flatten.toMap.asJsonObject
    )
  }

  private case class ApiKeysUpdate(
    description: Option[String],
    metadata: Option[String]
  )

  private object ApiKeysUpdate {
    implicit val encodeApiKeysUpdate: Encoder[ApiKeysUpdate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        value.description.map(_.asJson).map(("description", _)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** API Keys are used to authenticate to the <a
  * href="https://ngrok.com/docs/api#authentication">ngrok
  *  API</a>. You may use the API itself
  *  to provision and manage API Keys but you&#39;ll need to provision your first
  * API
  *  key from the <a href="https://dashboard.ngrok.com/api/keys">API Keys page</a>
  * on your
  *  ngrok.com dashboard.
  *
  * See also <a href="https://ngrok.com/docs/api#api-api-keys">https://ngrok.com/docs/api#api-api-keys</a>.
  */
class ApiKeys private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import ApiKeys._

  /** Create a new API key. The generated API key can be used to authenticate to the
    * ngrok API.
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys-create">https://ngrok.com/docs/api#api-api-keys-create</a>.
    *
    * @param description human-readable description of what uses the API key to authenticate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined data of this API key. optional, max 4096 bytes
    * @param ownerId If supplied at credential creation, ownership will be assigned to the specified User or Bot. Only admins may specify an owner other than themselves. Defaults to the authenticated User or Bot.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    description: Option[String] = None,
    metadata: Option[String] = None,
    ownerId: Option[String] = None
  ): Future[ApiKey] =
    apiClient.sendRequest[ApiKey](
      NgrokApiClient.HttpMethod.Post,
      "/api_keys",
      List.empty,
      Option(
        ApiKeysCreate(
          description,
          metadata,
          ownerId
        ).asJson
      )
    )

  /** Delete an API key by ID
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys-delete">https://ngrok.com/docs/api#api-api-keys-delete</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def delete(
    id: String
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/api_keys/$id",
      List.empty,
      Option.empty
    )

  /** Get the details of an API key by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys-get">https://ngrok.com/docs/api#api-api-keys-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[ApiKey] =
    apiClient.sendRequest[ApiKey](
      NgrokApiClient.HttpMethod.Get,
      s"/api_keys/$id",
      List.empty,
      Option.empty
    )

  /** List all API keys owned by this account
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys-list">https://ngrok.com/docs/api#api-api-keys-list</a>.
    *
    * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
    * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None
  ): Future[Page[ApiKeyList]] =
    apiClient
      .sendRequest[ApiKeyList](
        NgrokApiClient.HttpMethod.Get,
        "/api_keys",
        List(
          ("before_id", beforeId),
          ("limit", limit)
        ),
        Option.empty
      )
      .map(new Page(apiClient, _))

  /** Update attributes of an API key by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-api-keys-update">https://ngrok.com/docs/api#api-api-keys-update</a>.
    *
    * @param id the value of the <code>id</code> parameter as a [[scala.Predef.String]]
    * @param description human-readable description of what uses the API key to authenticate. optional, max 255 bytes.
    * @param metadata arbitrary user-defined data of this API key. optional, max 4096 bytes
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def update(
    id: String,
    description: Option[String] = None,
    metadata: Option[String] = None
  ): Future[ApiKey] =
    apiClient.sendRequest[ApiKey](
      NgrokApiClient.HttpMethod.Patch,
      s"/api_keys/$id",
      List.empty,
      Option(
        ApiKeysUpdate(
          description,
          metadata
        ).asJson
      )
    )

}
