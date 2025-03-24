name := "productcontrol"

version := "1.0-SNAPSHOT"

scalaVersion := "3.3.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice,
  "org.apache.pekko" %% "pekko-stream" % "1.0.2"
)