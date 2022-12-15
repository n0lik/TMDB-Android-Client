/**
 * Basic android configuration plugin.
 * The plugin contains common configuration for android modules.
 * The specific setups must be added in specific module.
 */
import com.android.build.gradle.BaseExtension
import com.android.builder.internal.BaseConfigImpl

configure<BaseExtension> {

    setCompileSdkVersion(33)
    buildToolsVersion = "33.0.1"

    defaultConfig {
        minSdk = 23
        setTargetSdkVersion(33)

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding {
        isEnabled = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            addApplicationSuffixId(".debug")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun BaseConfigImpl.addApplicationSuffixId(
    suffixId: String
) {
    plugins.withId("com.android.application") {
        applicationIdSuffix(suffixId)
    }
}