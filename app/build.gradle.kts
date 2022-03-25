plugins {
    id("android-application-convention")
}

android {
    defaultConfig {
        applicationId = "com.n0lik.sample"
    }
}

dependencies {
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraint)

    testImplementation(libs.junitCore)
    androidTestImplementation(libs.junitAndroid)
    androidTestImplementation(libs.espresso)
}