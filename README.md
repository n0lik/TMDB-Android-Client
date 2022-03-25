## Sample project

Hi anyone! This is my pet project. It will be supported by me at free time.

## Goal

Sharing ideas, approaches and its implementations with community.

### Gradle configuration

The main idea is to separate project build logic using [gradle plugins](https://docs.gradle.org/current/userguide/plugins.html).

__There are basic plugins:__

* [android-application-convention plugin](/build-logic/src/main/kotlin/android-application-convention.gradle.kts) -
  uses for configuring __android application module__;
* [android-library-convention plugin](/build-logic/src/main/kotlin/android-library-convention.gradle.kts) -
  uses for configuring __android library modules__;
* [basic-android-convention plugin](/build-logic/src/main/kotlin/basic-android-convention.gradle.kts) -
  configures android modules;
* [basic-kotlin-convention plugin](/build-logic/src/main/kotlin/basic-kotlin-convention.gradle.kts) -
  applies __Kotlin's compile configuration__ to each modules;
* [kotlin-jvm-convention plugin](/build-logic/src/main/kotlin/kotlin-jvm-convention.gradle.kts) -
  uses for configuring __Kotlin__ in modules;
* [ktlint-convention plugin](/build-logic/src/main/kotlin/ktlint-convention.gradle.kts) -
  uses for configuring [ktlint plugin](https://github.com/JLLeitschuh/ktlint-gradle) in modules.

__Gradle Version Catalog__

Read official documentation [here](https://docs.gradle.org/current/userguide/platforms.html).
> Note!  
> Version catalog don't accessible from precompiled script plugins  
> Issue (https://github.com/gradle/gradle/issues/15383#issuecomment-779893192)

By default, versions of a dependency declared in the file `gradle/libs.versions.toml`.
If you wish to add dependency to the project, just declare a version of a dependency in the one of [4 major sections](https://docs.gradle.org/current/userguide/platforms.html#sub::toml-dependencies-format) in the TOML file and press __Sync__ project.
Now, your can pick from when declaring dependencies in a build script.
The dependency coordinates can be picked from a version catalog (example, for __build.gradle.kts__):
```sh
dependencies {
    implementation(libs.{your_dependency_name})
}
```

Have a good coding:)