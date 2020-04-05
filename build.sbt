name := "trial_one"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "1.1.2"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-stream
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"

// https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-reactivestreams
libraryDependencies += "org.mongodb" % "mongodb-driver-reactivestreams" % "1.11.0"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "1.1.2"

// https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "3.5.1"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.1.2"

// https://mvnrepository.com/artifact/com.typesafe.slick/slick-hikaricp
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"

// https://mvnrepository.com/artifact/com.typesafe.slick/slick
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.2"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.19"

// https://mvnrepository.com/artifact/org.mongodb.scala/mongo-scala-driver
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0"

// https://mvnrepository.com/artifact/org.scala-lang/scala-library
libraryDependencies += "org.scala-lang" % "scala-library" % "2.12.7"

val AkkaVersion = "2.5.23"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-kafka" % "2.0.2",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion
)

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5"