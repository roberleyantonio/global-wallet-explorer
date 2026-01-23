import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    "kotlin"
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.apollo)
}

kotlin {
    androidTarget {}

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "network"
            isStatic = true
            freeCompilerArgs += listOf(
                "-Xbinary=bundleId=br.com.dev360.globalwalletexplorer.corenetwork"
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)

            api(libs.apollo.runtime)
            implementation(libs.apollo.normalized.cache)

            implementation(project(path = ":core:shared"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "br.com.dev360.globalwalletexplorer.corenetwork"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

apollo {
    service("service") {
        packageName.set("br.com.dev360.globalwalletexplorer.corenetwork")
        srcDir("src/commonMain/graphql")
        schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
        mapScalar("BigDecimal", "kotlin.String")
        mapScalar("Date", "kotlin.String")
        introspection {
            endpointUrl.set("https://swop.cx/graphql")
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
            headers.put("Authorization", "your_api_key_here")
        }
    }
}