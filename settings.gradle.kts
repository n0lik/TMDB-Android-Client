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
            when {
                pluginId.startsWith("org.jetbrains.kotlin") -> useVersion("1.6.10")
                pluginId.startsWith("com.android.") -> useVersion("7.1.0")
                pluginId == "org.jlleitschuh.gradle.ktlint" -> useVersion("10.2.1")
            }
        }
    }
}