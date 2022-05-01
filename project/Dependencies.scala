import sbt._

object Dependencies {
  lazy val versions = Map(
    "armeria" -> "1.15.0",
    "circe" -> "0.14.1",
    "scala-java8" -> "1.0.2",
    "scalatest" -> "3.2.11",
    "slf4j" -> "1.7.36",
    "wiremock" -> "2.32.0"
  )

  lazy val scalaJava8 = "org.scala-lang.modules" %% "scala-java8-compat" % versions("scala-java8")

  lazy val armeriaBom = "com.linecorp.armeria" % "armeria-bom" % versions("armeria") pomOnly()
  lazy val armeria = "com.linecorp.armeria" % "armeria" % versions("armeria")

  lazy val circeCore = "io.circe" %% "circe-core" % versions("circe")
  lazy val circeParser = "io.circe" %% "circe-parser" % versions("circe")

  lazy val scalaTest = "org.scalatest" %% "scalatest" % versions("scalatest") % Test
  lazy val wiremock = "com.github.tomakehurst" % "wiremock-jre8" % versions("wiremock") % Test
  lazy val slf4jSimple = "org.slf4j" % "slf4j-simple" % versions("slf4j") % Test
}
