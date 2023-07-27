/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object ApplicationSessions {
}

/**
 * An API client for [[ApplicationSessions]].
 *
 * See also <a href="https://ngrok.com/docs/api#api-application-sessions">https://ngrok.com/docs/api#api-application-sessions</a>.
 */
class ApplicationSessions private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import ApplicationSessions._
  /**
   * Get an application session by ID.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-sessions-get">https://ngrok.com/docs/api#api-application-sessions-get</a>.
   *
   * @param id a resource identifier
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def get(
    id: String,
  ): Future[ApplicationSession] =
    apiClient.sendRequest[ApplicationSession](
      NgrokApiClient.HttpMethod.Get,
      s"/app/sessions/${id}",
      List.empty,
      Option.empty
    )

  
  /**
   * Delete an application session by ID.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-sessions-delete">https://ngrok.com/docs/api#api-application-sessions-delete</a>.
   *
   * @param id a resource identifier
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def delete(
    id: String,
  ): Future[Unit] =
    apiClient.sendRequest[Unit](
      NgrokApiClient.HttpMethod.Delete,
      s"/app/sessions/${id}",
      List.empty,
      Option.empty
    )

  
  /**
   * List all application sessions for this account.
   *
   * See also <a href="https://ngrok.com/docs/api#api-application-sessions-list">https://ngrok.com/docs/api#api-application-sessions-list</a>.
   *
   * @param beforeId the value of the <code>before_id</code> parameter as a [[scala.Predef.String]]
   * @param limit the value of the <code>limit</code> parameter as a [[scala.Predef.String]]
   * @return a [[scala.concurrent.Future]] encapsulating the API call's result
   */
  def list(
    beforeId: Option[String] = None,
    limit: Option[String] = None,
  ): Future[Page[ApplicationSessionList]] =
    apiClient.sendRequest[ApplicationSessionList](
      NgrokApiClient.HttpMethod.Get,
      "/app/sessions",
      List(
        ("before_id", beforeId),
        ("limit", limit),
      ),
      Option.empty
    ).map(new Page(apiClient, _))

  }
