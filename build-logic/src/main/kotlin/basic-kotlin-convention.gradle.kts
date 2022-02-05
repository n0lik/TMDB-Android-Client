/**
 * Basic kotlin configuration plugin.
 * It contains the kotlin compile configuration.
 * The plugin configures Kotlin for each module.
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}