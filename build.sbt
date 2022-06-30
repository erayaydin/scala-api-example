val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"
val PureConfigVersion = "0.17.1"
val SlickVersion = "3.3.3"
val Slf4jVersion = "1.7.36"
val FlywayVersion = "8.5.12"
val AkkaHttpCorsVersion = "1.1.3"
val PostgreDriverVersion = "42.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "ApiExample",
    version := "0.1.0-SNAPSHOT",

    inThisBuild(List(
      organization := "in.yayd.era.api_example",
      scalaVersion := "2.13.8"
    )),

    libraryDependencies ++= Seq(
      // Akka
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,

      // Config File Parser
      "com.github.pureconfig" %% "pureconfig" % PureConfigVersion,

      // Postgres driver
      "org.postgresql" % "postgresql" % PostgreDriverVersion,

      // Slick
      "com.typesafe.slick" %% "slick" % SlickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,

      // Flyway
      "org.flywaydb" % "flyway-core" % FlywayVersion,

      // Slf4j
      "org.slf4j" % "slf4j-nop" % Slf4jVersion,

      // CORS support
      "ch.megard" %% "akka-http-cors" % AkkaHttpCorsVersion
    )
  )

unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}