name := "Spark-streaming"

version := "0.1"

scalaVersion := "2.13.5"

val AkkaVersion = "2.5.31"
libraryDependencies ++= Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "2.0.2",
  "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "2.0.2",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion
)
// vazy pas de problèmes
//je teste un truc vite fait
