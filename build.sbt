name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += specs2 % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test


