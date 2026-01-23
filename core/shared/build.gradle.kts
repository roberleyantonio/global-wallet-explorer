plugins {
    "kotlin"
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=br.com.dev360.globalwalletexplorer.coreshared"
            )
        }
    }
    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)
            api(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            api(libs.koin.android)
        }
    }
}

android {
    namespace = "br.com.dev360.globalwalletexplorer.coreshared"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}