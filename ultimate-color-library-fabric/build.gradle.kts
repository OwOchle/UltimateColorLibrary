plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    application
    signing
    `maven-publish`
    id("fabric-loom") version "1.3-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}


group = "app.moreo"
version = parent?.version ?: "?-SNAPSHOT"

val loaderVersion: String by properties
val minecraftVersion: String by properties
val yarnMappings: String by properties
val fabricVersion: String by properties

val mcModVersion = "0.0.10"

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

loom {
    splitEnvironmentSourceSets()

    mods {
        this.create("ultimate-color-library") {
            sourceSet("main")
        }
    }
}

dependencies {
    api(project(":ultimate-color-library-minecraft"))?.let { shadow(it) }
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
}

tasks.processResources {
    inputs.property("version", mcModVersion)

    filesMatching("fabric.mod.json") {
        expand("version" to mcModVersion)
    }
}

application {
    project.setProperty("mainClassName", "app.moreo.ucl")
}

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

tasks.dokkaHtml {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}

tasks.jar {
    from("../LICENSE.md") {
        rename {
            "${it}_${project.base.archivesName.get()}"
        }
    }
}

publishing {
    repositories {
        maven {
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenKotlin") {
            artifactId = "ultimate-color-library-fabric"
            groupId = "app.moreo"
            version = project.version.toString()

            artifact(tasks.getByName("dokkaJavadocJar"))
            artifact(tasks.kotlinSourcesJar)

            from(components["kotlin"])
            pom {
                name.set("Ultimate Color Library")
                description.set("A library for color manipulation and interpolation")
                url.set("https://github.com/MoreOwO/UltimateColorLibrary")
                packaging = "jar"

                licenses {
                    license {
                        name.set("Creative Commons Attribution-ShareAlike 4.0 International")
                        url.set("https://creativecommons.org/licenses/by-sa/4.0/")
                    }
                }

                developers {
                    developer {
                        id.set("moreowo")
                        name.set("MoréOwO")
                        email.set("main@moreo.app")
                        url.set("https://github.com/MoreOwO")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/MoreOwO/UltimateColorLibrary.git")
                    developerConnection.set("scm:git:git@github.com:MoreOwO/UltimateColorLibrary.git")
                    url.set("https://github.com/MoreOwO/UltimateColorLibrary")
                }
            }
        }
    }
}

tasks.shadowJar {
    configurations = listOf(project.configurations.getByName("shadow"))

    minimize()

    archiveClassifier.set("mod")
}


signing {
    useGpgCmd()
    sign(publishing.publications["mavenKotlin"])
}
