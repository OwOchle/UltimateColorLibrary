plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.8.20"
}

repositories {
    mavenCentral()
}

version = "0.0.6-alpha-SNAPSHOT"

tasks.dokkaHtml {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}

tasks.dokkaHtmlMultiModule {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}