plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.8.20"
}

repositories {
    mavenCentral()
}

version = "0.0.9-alpha-SNAPSHOT"

tasks.dokkaHtml {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}

tasks.dokkaHtmlMultiModule {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}

tasks.register("publishEverything") {
    dependsOn(":ultimate-color-library-core:publishMavenKotlinPublicationToMavenRepository")
    dependsOn(":ultimate-color-library-bukkit:publishMavenKotlinPublicationToMavenRepository")
    dependsOn(":ultimate-color-library-minecraft:publishMavenKotlinPublicationToMavenRepository")
    dependsOn(":ultimate-color-library-serialization:publishMavenKotlinPublicationToMavenRepository")
}