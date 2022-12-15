rootProject.name = "TMDB Android Client"

includeBuild("build-logic")

include(
    ":app",
    ":movie-api",
    ":movie-impl",
    ":common-api",
    ":common-impl",
    ":common-ui",
    ":genres-api",
    ":genres-impl",
    ":common-test"
)

/**
 * All libs' versions has been declared in the project's file (/gradle/libs.versions.toml)
 * [Gradle version catalog feature](https://docs.gradle.org/current/userguide/platforms.html)
 * @since Gradle 7.4
 */
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            val pluginId = requested.id.id
            println("PluginId = $pluginId")
            when {
                pluginId.startsWith("org.jetbrains.kotlin") -> useVersion("1.7.22")
                pluginId.startsWith("com.android.") -> useVersion("7.3.1")
                pluginId == "io.gitlab.arturbosch.detekt" -> useVersion("1.22.0")
            }
        }
    }
}