import sbt._
import Keys._
import Dependencies._

object Resolvers {
  val eknet = "eknet.org" at "https://eknet.org/maven2"
  val milton = "milton.io" at "http://milton.io/maven"
}
object Version {
  val slf4j = "1.7.2"
  val logback = "1.0.9"
  val scalaTest = "2.0.M6-SNAP3"
  val grizzled = "0.6.9"
  val scala = "2.9.2"
  val servlet = "3.0.1"
  val publet = "1.0.1"
}

object Dependencies {

  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % "test"
  val publetAppDev = "org.eknet.publet" %% "publet-app" % Version.publet exclude("org.restlet.jse", "org.restlet.ext.fileupload") exclude("org.restlet.jse", "org.restlet") exclude("org.slf4j", "slf4j-log4j12")
  val publetAppPlugin = publetAppDev % "publet" exclude("org.restlet.jse", "org.restlet.ext.fileupload") exclude("org.restlet.jse", "org.restlet") exclude("org.slf4j", "slf4j-log4j12")

  val publetWeb = "org.eknet.publet" %% "publet-web" % Version.publet % "provided" exclude("org.restlet.jse", "org.restlet.ext.fileupload") exclude("org.restlet.jse", "org.restlet") exclude("org.slf4j", "slf4j-log4j12")
  val servletApiProvided = "javax.servlet" % "javax.servlet-api" % Version.servlet % "provided"
}

// Root Module 

object RootBuild extends Build {
  import org.eknet.publet.sbt.PubletPlugin

  lazy val root = Project(
    id = "sbt__projectId__",
    base = file("."),
    settings = buildSettings
  )

  //an empty project to create a classpath to be
  //able to start publet from the ide via a "main class"
  lazy val runner = Project(
    id = "publet-runner",
    base = file("runner"),
    settings = Project.defaultSettings ++ Seq(
      name := "publet-runner",
      libraryDependencies ++= Seq(publetAppDev)
    )
  ) dependsOn (root)

  val buildSettings = Project.defaultSettings ++ ReflectPlugin.allSettings ++ Seq(
    name := "__projectId__",
    ReflectPlugin.reflectPackage := "__groupId__",
    sourceGenerators in Compile <+= ReflectPlugin.reflect,
    libraryDependencies ++= deps
  ) ++ PubletPlugin.publetSettings

  override lazy val settings = super.settings ++ Seq(
    version := "0.1.0-SNAPSHOT",
    organization := "__groupId__",
    scalaVersion := Version.scala,
    exportJars := true,
    pomIncludeRepository := (_ => false),
    scalacOptions ++= Seq("-unchecked", "-deprecation"),
    resolvers ++= Seq(Resolvers.eknet, Resolvers.milton),
    licenses := Seq(("ASL2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))),

    // see https://jira.codehaus.org/browse/JETTY-1493
    ivyXML := <dependency org="org.eclipse.jetty.orbit" name="javax.servlet" rev="3.0.0.v201112011016">
        <artifact name="javax.servlet" type="orbit" ext="jar"/>
    </dependency>
  )

  val deps = Seq(publetWeb, publetAppPlugin, servletApiProvided)
}


