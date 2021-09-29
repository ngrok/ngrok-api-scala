package com.ngrok.definitions

import com.ngrok.NgrokApiClient
import io.circe.Decoder

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

/** Wrapper class that holds a single page of a [[Pageable]] response.
  *
  * @constructor creates a new page.
  * @param apiClient the API client used to fetch the page
  * @param page the page itself
  * @tparam T underlying page type
  */
class Page[T <: Pageable: Decoder: ClassTag](apiClient: NgrokApiClient, val page: T) {

  /** Fetches the next page of responses, if any.
    *
    * @param ec an execution context
    * @return a future encapsulating a possible next page
    */
  def next()(implicit ec: ExecutionContext): Future[Option[Page[T]]] =
    page.nextPageUri.fold(
      Future.successful(Option.empty[Page[T]])
    )(uri =>
      apiClient
        .sendRequest[T](uri)
        .map(nextPage => Option(new Page(apiClient, nextPage)))
    )

  override def equals(obj: Any): Boolean = obj match {
    case other: Page[_] => page.equals(other.page)
    case _              => false
  }

  override def hashCode(): Int = page.hashCode()

  override def toString: String = s"Page($page)"
}
