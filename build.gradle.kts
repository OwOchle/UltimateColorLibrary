plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
    signing
}

group = "app.moreo.ucl"
version = "0.0.1-ALPHA"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            artifactId = "UltimateColorLibrary"
            groupId = "app.moreo"
            version = project.version.toString()

            from(components["kotlin"])
            pom {
                name.set("Ultimate Color Library")
                description.set("A library for color manipulation and interpolation")
                url.set("https://github.com/MoreOwO/UltimateColorLibrary")

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

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenKotlin"])
}
