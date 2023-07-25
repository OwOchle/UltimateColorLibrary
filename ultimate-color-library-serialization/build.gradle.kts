plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.8.21"
    id("org.jetbrains.dokka")
    application
    signing
    `maven-publish`
}

group = "app.moreo"
version = "0.0.8-alpha-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":ultimate-color-library-core"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}