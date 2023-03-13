package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object ApplicationUsers {
}

/**
 * An API client for [[ApplicationUsers]].
 *
 * See also <a href="https://ngrok.com/docs/api#api-application-users">https://ngrok.com/docs/api#api-application-users</a>.
 */
class ApplicationUsers private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import ApplicationUsers._
  /**
   * Get an application user by ID.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-users-get">https://ngrok.com/docs/api#api-application-users-get</a>.
   *
   * @param id a resource identifier
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def get(
    id: String,
  ): Future[ApplicationUser] =
    apiClient.sendRequest[ApplicationUser](
      NgrokApiClient.HttpMethod.Get,
      s"/app/users/${id}",
      List.empty,
      Option.empty
    )

  
  /**
   * Delete an application user by ID.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-users-delete">https://ngrok.com/docs/api#api-application-users-delete</a>.
   *
   * @param id a resource identifier
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def delete(
    id: String,
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/app/users/${id}",
      List.empty,
      Option.empty
    )

  
  /**
   * List all application users for this account.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-users-list">https://ngrok.com/docs/api#api-application-users-list</a>.
   *
   * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
   * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None,
  ): Future[Page[ApplicationUserList]] =
    apiClient.sendRequest[ApplicationUserList](
      NgrokApiClient.HttpMethod.Get,
      "/app/users",
      List(
        ("before_id", beforeId),
        ("limit", limit),
      ),
      Option.empty
    ).map(new Page(apiClient, _))

  }
