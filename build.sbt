/* Code generated for API Clients. DO NOT EDIT. */

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.util.stream.Collectors
import scala.collection.JavaConverters._

import Dependencies._

ThisBuild / organization := "com.ngrok"
ThisBuild / organizationName := "ngrok"
ThisBuild / version := Version.version
ThisBuild / description := "ngrok API client for Scala applications"
ThisBuild / licenses := List("MIT" -> url("https://mit-license.org/"))
ThisBuild / homepage := Some(url("https://ngrok.com"))
ThisBuild / crossScalaVersions := Seq("2.13.8", "2.12.15")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.head

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/ngrok/ngrok-api-scala"),
    "scm:git@github.com:ngrok/ngrok-api-scala.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "ngrok",
    name = "ngrok",
    email = "",
    url = url("https://ngrok.com")
  )
)

ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / sonatypeProfileName := "com.ngrok"
ThisBuild / sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("ngrok", "ngrok-api-scala", ""))

ThisBuild / autoAPIMappings := true
ThisBuild / apiURL := Some(url("https://scala-api.docs.ngrok.com/"))

lazy val generateSources = taskKey[Seq[File]]("Generates sources from templates")
generateSources := {
  val templatesRoot = ((Compile / sourceDirectory).value / "scala-templates").getAbsoluteFile
  val generatedSourcesRoot = (Compile / sourceManaged).value.getAbsoluteFile

  Files
    .walk(templatesRoot.toPath)
    .collect(Collectors.toList[java.nio.file.Path])
    .asScala
    .filter(_.toString.endsWith(".scala"))
    .map({ path =>
      val contents = IO.read(path.toFile, StandardCharsets.UTF_8)
      val newContents = contents
        .replace("${project.version}", (ThisBuild / version).value)
      val outputPath = file(path.toAbsolutePath.toString.replace(templatesRoot.toString, generatedSourcesRoot.toString))
      IO.write(outputPath, newContents)
      outputPath
    })
}

lazy val root = (project in file("."))
  .settings(
    name := "ngrok-api-scala",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "utf-8",
      "-feature",
      "-unchecked",
      "-Xlint:adapted-args",
      "-Xlint:constant",
      "-Xlint:deprecation",
      "-Xlint:infer-any",
      "-Xlint:missing-interpolator",
      "-Xlint:nullary-unit",
      "-Xlint:private-shadow",
      "-Xlint:type-parameter-shadow",
    ),
    Compile / doc / scalacOptions ++= Seq(
      "-doc-title", "ngrok API Documentation",
      "-doc-version", s"(${version.value})",
      "-implicits",
      "-groups",
    ),
    libraryDependencies := Seq(
      scalaJava8,
      armeriaBom,
      armeria,
      circeCore,
      circeParser,
      scalaTest,
      wiremock,
      slf4jSimple,
    ),
    Compile / sourceGenerators += generateSources.taskValue,
    publishMavenStyle := true,
  )
