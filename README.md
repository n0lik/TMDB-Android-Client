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
  uses for configuring __Kotlin__ in modules.

Have a good coding:)