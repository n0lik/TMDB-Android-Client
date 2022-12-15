plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.movie.impl"
}

dependencies {
    implementation(project(":movie-api"))
    implementation(project(":common-api"))
    implementation(project(":common-ui"))
    implementation(project(":genres-api"))
    implementation(project(":genres-impl"))

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navUi)
    implementation(libs.androidx.navFragment)

    implementation(libs.paging.runtime)

    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.adapterDelegate)

    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit.core)
    implementation(libs.ktor.android)

    testImplementation(project(":common-test"))
    testImplementation(libs.junit.core)
    testImplementation(libs.mockk.core)
    testImplementation(libs.coroutine.test)

    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}