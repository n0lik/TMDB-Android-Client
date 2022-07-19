plugins {
    id("android-library-convention")
}

dependencies {
    implementation(libs.dagger.core)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.retrofit.core)
    implementation(libs.ktor.android)
    implementation(libs.kotlin.serialization)
}