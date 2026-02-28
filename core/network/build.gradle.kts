import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.apollo)
    alias(libs.plugins.buildkonfig.plugin)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val apiKey: String = System.getenv("SWOP_API_KEY")
    ?: localProperties.getProperty("SWOP_API_KEY")
    ?: ""

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
            api(libs.apollo.normalized.cache)
            implementation(libs.apollo.normalized.cache.sqlite)

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
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
            headers.put("Authorization", "ApiKey $apiKey")
        }
    }
}

buildkonfig {
    packageName = "br.com.dev360.globalwalletexplorer.corenetwork"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "API_KEY", apiKey)
    }
}