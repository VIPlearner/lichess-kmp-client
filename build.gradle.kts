plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.detekt) apply true
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.0.1")
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }
}

// Configure detekt
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

// Convenience tasks for code quality
tasks.register("lintAll") {
    group = "verification"
    description = "Run all linting tools (ktlint and detekt)"
    dependsOn("ktlintCheck", "detekt")
}

tasks.register("formatAll") {
    group = "formatting"
    description = "Format code with ktlint"
    dependsOn("ktlintFormat")
}

tasks.register("checkAll") {
    group = "verification"
    description = "Run all checks including tests and linting"
    dependsOn("check", "ktlintCheck", "detekt")
}
