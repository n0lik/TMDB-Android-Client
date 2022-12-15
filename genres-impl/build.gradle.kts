plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.genres.impl"
}

dependencies {
    implementation(project(":genres-api"))
    implementation(project(":common-api"))

    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    implementation(libs.kotlin.serialization)
    implementation(libs.ktor.core)
    implementation(libs.retrofit.core)

    testImplementation(project(":common-test"))
    testImplementation(libs.junit.core)
    testImplementation(libs.mockk.core)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.ktor.mock)

    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}