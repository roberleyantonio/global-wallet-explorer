import org.jetbrains.compose.compose

plugins {
    "kotlin"
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
    id("dev.mokkery") version "3.1.1"
}

kotlin {
    androidTarget {}

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "home"
            isStatic = true
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=br.com.dev360.globalwalletexplorer.featurehome"
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(libs.compose.navigation)

            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(compose.materialIconsExtended)

            implementation(project(path = ":core:shared"))
            implementation(project(path = ":core:shared-ui"))
            implementation(project(path = ":core:network"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mokkery.gradle)
            implementation(libs.turbine)
            implementation(libs.ui.test)

            implementation(libs.koin.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.mockk)
        }
        iosTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "br.com.dev360.globalwalletexplorer.featurehome"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}