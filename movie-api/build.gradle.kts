plugins {
    id("android-library-convention")
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