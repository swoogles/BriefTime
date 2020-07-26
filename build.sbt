//scalaVersion := "2.12.10"
scalaVersion := "2.13.1"
version := "0.0.16"

organization := "com.billding"

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "dev.zio" %%% "zio" % "1.0.0-RC21-2",
  "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC5",
)

githubOwner := "swoogles"
githubRepository := "BriefTime"
