name := "mvp-sandbox-backend"

version := "1.0"

lazy val `mvp-sandbox-backend` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc ,
  cache ,
  javaWs ,
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc42" ,
  evolutions
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
