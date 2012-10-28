import sbt._
import Keys._
import Dependencies._

object Resolvers {
  val eknet = "eknet.org" at "https://eknet.org/maven2"
}
object Version {
  val slf4j = "1.6.4"
  val logback = "1.0.1"
  val scalaTest = "1.8"
  val grizzled = "0.6.9"
  val scala = "2.9.2"
  val servlet = "3.0.1"
  val publet = "1.0.0-SNAPSHOT"
}

object Dependencies {

  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % "test" withSources()
  val publetApp = "org.eknet.publet" %% "publet-app" % Version.publet % "publet"
  val publetWeb = "org.eknet.publet" %% "publet-web" % Version.publet % "provided" withSources()
  val servletApi = "javax.servlet" % "javax.servlet-api" % Version.servlet withSources()
  val servletApiProvided = servletApi % "provided"
}

// Root Module 

object RootBuild extends Build {
  import org.eknet.publet.sbt.PubletPlugin

  lazy val root = Project(
    id = "sbt__projectId__",
    base = file("."),
    settings = buildSettings
  ) 

  val buildSettings = Project.defaultSettings ++ Seq(
    name := "__projectId__",
    libraryDependencies ++= deps
  ) ++ PubletPlugin.publetSettings

  override lazy val settings = super.settings ++ Seq(
    version := "1.0.0-SNAPSHOT",
    organization := "__groupId__",
    scalaVersion := Version.scala,
    exportJars := true,
    scalacOptions ++= Seq("-unchecked", "-deprecation"),
    resolvers += Resolvers.eknet
/*    pomExtra := <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses> */
  )

  val deps = Seq(publetWeb, publetApp, servletApiProvided)
}


