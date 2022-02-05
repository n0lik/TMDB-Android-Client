/**
 * Basic android configuration plugin.
 * The plugin contains common configuration for android modules.
 * The specific setups must be added in specific module.
 */
import com.android.build.gradle.BaseExtension

configure<BaseExtension> {

    setCompileSdkVersion(32)
    buildToolsVersion = "32.0.0"

    defaultConfig {
        minSdk = 19
        setTargetSdkVersion(32)

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}