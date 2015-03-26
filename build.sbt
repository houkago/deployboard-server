import NativePackagerKeys._

packageArchetype.java_application

name := "finatra_example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.6.0",
  "org.scaldi" %% "scaldi" % "0.3.2",
  "org.scalaj" %% "scalaj-http" % "1.1.4"
)

resolvers ++= Seq(
  "Twitter" at "http://maven.twttr.com"
)
