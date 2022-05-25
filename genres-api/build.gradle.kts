plugins {
    id("android-library-convention")
}

dependencies {
    implementation(project(":common-api"))
    implementation(libs.kotlin.serialization)
}