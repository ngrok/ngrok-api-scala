package com.ngrok.definitions

import java.net.URI

/** Trait indicating a resource type that can have more than one page of responses. */
trait Pageable {

  /** Fetches the URI of the next page.
    *
    * @return a URI
    */
  def nextPageUri: Option[URI]
}
