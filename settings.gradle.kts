pluginManagement {
    repositories {
        mavenCentral()
        google()
        jcenter()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "chip-8"


include(":android")
include(":desktop")
include(":common")

