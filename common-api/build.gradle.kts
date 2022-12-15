plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.common.api"
}

dependencies {
    implementation(libs.dagger.core)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.retrofit.core)
    implementation(libs.ktor.android)
}