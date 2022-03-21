/**
 * Basic ktLint configuration plugin.
 * The plugin configures ktLint's rules for each module.
 */
import org.jlleitschuh.gradle.ktlint.KtlintExtension

configure<KtlintExtension> {
    version.set("0.43.2")
    android.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    filter {
        exclude("**/generated/**")
    }
}