plugins {
    kotlin("jvm")
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
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}