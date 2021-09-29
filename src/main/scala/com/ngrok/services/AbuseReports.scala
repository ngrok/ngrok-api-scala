package com.ngrok.services

import com.ngrok.NgrokApiClient
import com.ngrok.definitions._
import io.circe.Encoder
import io.circe.syntax._
import scala.concurrent.{ExecutionContext, Future}

object AbuseReports {
  private case class AbuseReportsCreate(
    urls: List[java.net.URI],
    metadata: Option[String]
  )

  private object AbuseReportsCreate {
    implicit val encodeAbuseReportsCreate: Encoder[AbuseReportsCreate] = Encoder.encodeJsonObject.contramap(value =>
      List(
        Option(("urls", value.urls.asJson)),
        value.metadata.map(_.asJson).map(("metadata", _))
      ).flatten.toMap.asJsonObject
    )
  }

}

/** Abuse Reports allow you to submit take-down requests for URLs hosted by
  *  ngrok that violate ngrok's terms of service.
  *
  * See also <a href="https://ngrok.com/docs/api#api-abuse-reports">https://ngrok.com/docs/api#api-abuse-reports</a>.
  */
class AbuseReports private[ngrok] (apiClient: NgrokApiClient)(implicit ec: ExecutionContext) {
  import AbuseReports._

  /** Creates a new abuse report which will be reviewed by our system and abuse
    * response team. This API is only available to authorized accounts. Contact
    * abuse@ngrok.com to request access
    *
    * See also <a href="https://ngrok.com/docs/api#api-abuse-reports-create">https://ngrok.com/docs/api#api-abuse-reports-create</a>.
    *
    * @param urls a list of URLs containing suspected abusive content
    * @param metadata arbitrary user-defined data about this abuse report. Optional, max 4096 bytes.
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def create(
    urls: List[java.net.URI],
    metadata: Option[String] = None
  ): Future[AbuseReport] =
    apiClient.sendRequest[AbuseReport](
      NgrokApiClient.HttpMethod.Post,
      "/abuse_reports",
      List.empty,
      Option(
        AbuseReportsCreate(
          urls,
          metadata
        ).asJson
      )
    )

  /** Get the detailed status of abuse report by ID.
    *
    * See also <a href="https://ngrok.com/docs/api#api-abuse-reports-get">https://ngrok.com/docs/api#api-abuse-reports-get</a>.
    *
    * @param id a resource identifier
    * @return a [[scala.concurrent.Future]] encapsulating the API call's result
    */
  def get(
    id: String
  ): Future[AbuseReport] =
    apiClient.sendRequest[AbuseReport](
      NgrokApiClient.HttpMethod.Get,
      s"/abuse_reports/$id",
      List.empty,
      Option.empty
    )

}
