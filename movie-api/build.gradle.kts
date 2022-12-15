plugins {
    id("android-library-convention")
}

android {
    namespace = "com.n0lik.sample.movie.api"
}

dependencies {

    implementation(project(":common-api"))
    implementation(project(":genres-api"))
    implementation(libs.androidx.core)

    implementation(libs.paging.runtime)
    implementation(libs.dagger.core)

    testImplementation(libs.junit.core)

    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}