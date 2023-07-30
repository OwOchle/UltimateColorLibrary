rootProject.name = "UltimateColorLibrary"
include("ultimate-color-library-core")
include("ultimate-color-library-minecraft")
include("ultimate-color-library-bukkit")
include("ultimate-color-library-fabric")
include("ultimate-color-library-serialization")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
}