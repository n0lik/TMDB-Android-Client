plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.common.ui"
}

dependencies {
    implementation(project(":common-api"))
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)
    implementation(libs.lifecycle.runtime)

    implementation(libs.lifecycle.viewmodel)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.adapterDelegate)

    implementation(libs.glide.glide)
    kapt(libs.glide.compiler)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}