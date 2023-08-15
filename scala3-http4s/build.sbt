val http4sVersion = "0.23.23"
val CirceVersion = "0.14.5"
val LogbackVersion = "1.4.11"
val MunitVersion = "0.7.29"
val MunitCatsEffectVersion = "1.0.7"
val JansiVersion = "2.4.0"
val ConfigVersion = "1.4.2"

lazy val root = (project in file("."))
  .settings(
    name := "http4s",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.3.0",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "com.typesafe" % "config" % ConfigVersion,
      "org.fusesource.jansi" % "jansi" % JansiVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test
    )
  )
