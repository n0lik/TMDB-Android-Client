rootProject.name = "Sample"

includeBuild("build-logic")

include("app")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

pluginManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            val pluginId = requested.id.id
            if (pluginId.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.6.10")
            } else if (requested.id.id.startsWith("com.android.")) {
                useVersion("7.1.0")
            }
        }
    }
}