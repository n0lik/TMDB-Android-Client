rootProject.name = "Sample"

includeBuild("build-logic")

include("app")

/**
 * All libs' versions has been declared in the project's file (/gradle/libs.versions.toml)
 * [Gradle version catalog feature](https://docs.gradle.org/current/userguide/platforms.html)
 * @since Gradle 7.4
 */
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
                pluginId.contains("kotlin-dsl") -> useVersion("1.6.10")
                pluginId.startsWith("org.jetbrains.kotlin") -> useVersion("1.6.10")
                pluginId.startsWith("com.android.") -> useVersion("7.1.2")
                pluginId == "org.jlleitschuh.gradle.ktlint" -> useVersion("10.2.1")
            }
        }
    }
}