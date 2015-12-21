import play.Project._

name := """hello-play-java"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.26",
  "org.webjars" %% "webjars-play" % "2.2.2", 
  "org.webjars" % "bootstrap" % "2.3.1",
  "com.github.jsonld-java" % "jsonld-java" % "0.7.0",
  "com.googlecode.json-simple" % "json-simple" % "1.1",
  "org.apache.jena" % "apache-jena-libs" % "3.0.1"
  )

playJavaSettings
