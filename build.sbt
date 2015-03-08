import NativePackagerKeys._

packageArchetype.java_application

name := "finatra_example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.6.0",
  "org.scaldi" %% "scaldi" % "0.3.2"
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"
