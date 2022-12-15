plugins {
    `version-catalog`
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
// VersionCatalog hasn't accessible yet for precompiled scripts
// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.7.22")
    implementation("org.jacoco:org.jacoco.core:0.8.8")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
}