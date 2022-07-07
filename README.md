# TMDB Android Client

Welcome to my pet project. It will be supported by me at free time.

This project exposes ideas, approaches and its implementations.

### Libraries

-  [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
-  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
-  [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
-  [NavComponent](https://developer.android.com/guide/navigation/navigation-getting-started)
-  [Ktor](https://ktor.io/docs/welcome.html)
-  [Retrofit](https://square.github.io/retrofit/)
-  [kotlin-serialization](https://kotlinlang.org/docs/serialization.html)
-  [Moshi](https://github.com/square/moshi)
-  [Dagger2](https://dagger.dev/)
-  [Mockk](https://mockk.io/)
-  [Glide](https://bumptech.github.io/glide/)
-  [Room](https://developer.android.com/jetpack/androidx/releases/room)

### Settings

If you desire to try this app, you have to do few steps:  
1. remove suffix *.example* for the file `api-config.properties.example`
2. add API key to the file.

If you don't have it, you'll be able generate it [there](https://developers.themoviedb.org/3)
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
* [detekt-convention plugin](/build-logic/src/main/kotlin/detekt-convention.gradle.kts) -
  uses for configuring [detekt plugin](https://detekt.dev/docs/gettingstarted/gradle/) in modules.
* [jacoco-convention plugin](/build-logic/src/main/kotlin/jacoco-convention.gradle.kts) -
  uses for configuring [jacoco plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html) in modules.

### Gradle Version Catalog
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
### Tests
[MockKtorHttpClient](/common-test/src/main/java/com/n0lik/common/test/ktor/MockKtorHttpClient.kt) - solution for __mocking responses__ over [Ktor](https://ktor.io/docs/http-client-testing.html) library.

Have a good coding:)