import java.io.FileInputStream
import java.util.Properties

plugins {
    id("android-library-convention")
}

android {
    buildTypes {
        all {
            val config = file("$rootDir/api-config.properties")
            val properties = Properties().also { it.load(FileInputStream(config)) }
            buildConfigField("String", "API_URL", properties.getProperty("api.url"))
            buildConfigField("String", "API_KEY", properties.getProperty("api.key"))
        }
    }
}

dependencies {
    implementation(project(":common-api"))
    
    implementation(libs.androidx.core)
    implementation(libs.androidx.securityCrypto)

    kapt(libs.dagger.compiler)
    implementation(libs.dagger.core)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.serialization)
    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.android)
    implementation(libs.ktor.content)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.serialization)

    implementation(libs.lifecycle.viewmodel)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}