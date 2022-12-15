plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.genres.api"
}

dependencies {
    implementation(project(":common-api"))
    implementation(libs.kotlin.serialization)
}