/**
 * Basic Detekt convention plugin.
 */
plugins {
    id("io.gitlab.arturbosch.detekt")
    id("basic-detekt-convention")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$rootDir/config/detekt/detekt.yml")
}