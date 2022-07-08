plugins {
    id("android-library-convention")
}

dependencies {
    implementation(project(":common-api"))
    implementation(libs.coroutine.core)
    implementation(libs.ktor.core)
    implementation(libs.ktor.mock)
    implementation(libs.ktor.content)
    implementation(libs.ktor.serialization)

    implementation(libs.retrofit.core)
    implementation(libs.mockk.core)
}