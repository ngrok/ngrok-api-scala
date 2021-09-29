package com.ngrok

/** Client version information. */
object Version {
  /** The ngrok API version in use. */
  final val ApiVersion: String = "2"

  /** This API client's version string */
  final val ClientVersion: String = "${project.version}"
}
