/* Code generated for API Clients. DO NOT EDIT. */

package com.ngrok.definitions

import io.circe.{Decoder, Encoder}

/** Representation of an API error from ngrok.
  *
  * @constructor creates a new ngrok API error.
  * @param message descriptive error message.
  * @param httpStatusCode HTTP status returned from the server
  * @param errorCode ngrok-specific error code, if any
  * @param details key-value map of error details
  * @param cause exception cause, if any
  */
case class NgrokApiError(
  message: String,
  httpStatusCode: Int,
  errorCode: Option[String],
  details: Map[String, String],
  cause: Option[Throwable]
) extends Exception(message, cause.orNull) {

  /** Checks if this error object contains one of the provided codes.
    *
    * @param codes error codes to check
    * @return whether or not one of the codes matches what is in this error object
    */
  def isErrorCode(codes: String*): Boolean = errorCode.exists(codes.contains)
}

object NgrokApiError {
  implicit val ngrokApiErrorEncoder: Encoder[NgrokApiError] = Encoder.forProduct4(
    "msg",
    "status_code",
    "error_code",
    "details"
  )(error => (error.message, error.httpStatusCode, error.errorCode, error.details))

  implicit val ngrokApiErrorDecoder: Decoder[NgrokApiError] = Decoder.forProduct4(
    "msg",
    "status_code",
    "error_code",
    "details"
  )((msg: Option[String], statusCode: Int, errorCode: Option[String], details: Option[Map[String, String]]) =>
    NgrokApiError(
      msg.getOrElse(s"HTTP server returned status $statusCode"),
      statusCode,
      errorCode,
      details.getOrElse(Map.empty),
      None
    )
  )

  /** Creates a new ngrok API error.
    *
    * @param httpStatusCode HTTP status returned from the server
    * @param responseBody response body returned from server, if any
    * @param cause exception cause
    * @return a new [[NgrokApiError]]
    */
  def apply(httpStatusCode: Int, responseBody: Option[String], cause: Throwable): NgrokApiError =
    NgrokApiError(
      cause.getMessage,
      httpStatusCode,
      errorCode = None,
      details = responseBody.fold(Map.empty[String, String])(b => Map("response_body" -> b)),
      Some(cause)
    )

  /** Creates a new ngrok API error.
    *
    * @param httpStatusCode HTTP status returned from the server
    * @return a new [[NgrokApiError]]
    */
  def apply(httpStatusCode: Int): NgrokApiError =
    NgrokApiError(
      s"HTTP server returned status $httpStatusCode",
      httpStatusCode,
      errorCode = None,
      details = Map.empty,
      cause = None
    )

}

object NgrokApiErrorHttpStatus {
  def unapply(error: NgrokApiError): Some[Int] = Some(error.httpStatusCode)
}

object NgrokApiErrorCode {
  def unapply(error: NgrokApiError): Option[String] = error.errorCode
}
