plugins {
    id("android-library-convention")
}

android {
    namespace = "com.en0lik.common.test"
}

dependencies {
    implementation(project(":common-api"))
    implementation(libs.coroutine.core)
    implementation(libs.ktor.core)
    implementation(libs.ktor.mock)
    implementation(libs.ktor.content)
    implementation(libs.ktor.serialization)
}