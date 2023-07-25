plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    application
    signing
    `maven-publish`
}

group = "app.moreo"
version = parent?.version ?: "?-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":ultimate-color-library-minecraft"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}