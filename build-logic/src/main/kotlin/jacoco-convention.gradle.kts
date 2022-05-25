plugins {
    id("jacoco")
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.8"
}

task<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        html.required.set(true)
    }
    sourceDirectories.setFrom(
        files(
            "${project.projectDir}/src/main/java"
        )
    )
    classDirectories.setFrom(
        fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
            exclude(
                setOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "android/**/*.*",
                    "**/*Module.*",
                    "**/*Dagger*.*",
                    "**/*MembersInjector*.*",
                    "**/*_Factory*.*",
                    "**/*_Provide*Factory*.*"
                )
            )
        }
    )
    executionData.setFrom(
        fileTree("${project.buildDir}") {
            include("jacoco/testDebugUnitTest.exec")
        }
    )
}