plugins {
    id("android-application-convention")
}

android {
    defaultConfig {
        applicationId = "com.n0lik.sample"
    }
}

dependencies {

    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    implementation(project(":movie-api"))
    implementation(project(":movie-impl"))
    implementation(project(":common-api"))
    implementation(project(":common-impl"))

    implementation(libs.retrofit.core)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraint)
    implementation(libs.androidx.navFragment)
    implementation(libs.androidx.navUi)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)
    implementation(libs.lifecycle.runtime)

    testImplementation(libs.junit.core)

    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}