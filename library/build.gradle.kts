import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.kotlinx.serialization)
}

group = "io.github.viplearner"
version = "0.1.0-alpha"

kotlin {
    jvm()
    androidLibrary {
        namespace = "io.github.viplearner.lichess.client"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(
                    JvmTarget.JVM_11,
                )
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    js(IR) {
        browser {
            testTask {
                useKarma {
                    useSafari()
                    useChrome()
                    useFirefox()
                }
            }
        }
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {
            // put your multiplatform dependencies here
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.korlibs.crypto)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.coroutines.test)
        }

        // Platform-specific Ktor engines
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            implementation(libs.ktor.client.curl)
        }

        // JS engine (if JS target is added in the future)
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "lichess-kmp-client", version.toString())

    pom {
        name = "Lichess KMP Client"
        description = "A Kotlin Multiplatform client library for the Lichess.org API"
        inceptionYear = "2025"
        url = "https://github.com/VIPlearner/lichess-kmp-client/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "viplearner"
                name = "viplearner"
                url = "https://github.com/VIPlearner/"
            }
        }
        scm {
            url = "https://github.com/VIPlearner/lichess-kmp-client/"
            connection = "scm:git:git://github.com/VIPlearner/lichess-kmp-client.git"
            developerConnection = "scm:git:ssh://git@github.com/VIPlearner/lichess-kmp-client.git"
        }
    }
}
