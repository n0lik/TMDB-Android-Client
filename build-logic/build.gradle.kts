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
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    implementation("org.jacoco:org.jacoco.core:0.8.8")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.20.0")
}