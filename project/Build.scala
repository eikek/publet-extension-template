/*
 * Copyright 2012 Eike Kettner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt._
import Keys._
import Dependencies._
import org.eknet.publet.sbt._

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
  val publetWeb = "org.eknet.publet" %% "publet-web" % Version.publet % "provided" withSources()
  val servletApi = "javax.servlet" % "javax.servlet-api" % Version.servlet withSources()
  val servletApiProvided = servletApi % "provided"
}

// Root Module 

object RootBuild extends Build {

  lazy val root = Project(
    id = "${projectId}",
    base = file("."),
    settings = buildSettings
  ) 

  val buildSettings = Project.defaultSettings ++ Seq(
    name := "${projectId}",
    libraryDependencies ++= deps
  ) ++ PubletSbtPlugin.publetSettings

  override lazy val settings = super.settings ++ Seq(
    version := "1.0.0-SNAPSHOT",
    organization := "${groupId}",
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

  val deps = Seq(publetWeb, servletApiProvided)
}


