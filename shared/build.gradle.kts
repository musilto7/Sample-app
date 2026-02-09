import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("org.openapi.generator")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

val openapiOutputDir = layout.buildDirectory.dir("generated/openapi").get().asFile

compose {
    resources {
        packageOfResClass = "cz.musilto5.myflickerapp.generated.resources"
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    val xcf = XCFramework()
    listOf(
        iosSimulatorArm64(),
        iosArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "cz.musilto5.myflickerapp.shared")
            // Kotlin/Native Release framework linking can OOM in devirtualization/LTO.
            // If you still hit OOM after increasing heap, keep this enabled for Release builds.
            if (buildType.name.equals("RELEASE", ignoreCase = true)) {
                freeCompilerArgs += listOf("-Xdisable-phases=DevirtualizationAnalysis")
            }
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.components.resources)
            implementation(libs.koin.compose)
            implementation(libs.androidx.lifecycle.savedstate)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.nav3)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.multiplatform)
            implementation(libs.navigation3.ui)
            implementation(libs.coil3.compose)
            implementation(libs.coil3.network.ktor3)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
        }

        // Note: androidMain dependencies are handled in the top-level
        // dependencies block below to avoid DSL scope issues with BOMs and Desugaring.
    }
}

android {
    namespace = "cz.musilto5.myflickerapp.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Top-level dependencies block handles "androidMain" specific logic
// better for Application modules (allows 'coreLibraryDesugaring' and 'platform')
dependencies {
    // KMP Android specific implementations
    implementation(libs.ktor.client.okhttp)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Using BOM (Platform) works correctly here
    implementation(platform(libs.androidx.compose.bom))

    // Desugaring works correctly here
    coreLibraryDesugaring(libs.core.library.desugaring)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

val openapiSrcRoot = openapiOutputDir.resolve("src/commonMain/kotlin")
kotlin.sourceSets.getByName("commonMain").kotlin.srcDir(openapiSrcRoot)

val packageName = "cz.musilto5.myflickerapp.data"
val apiPackageName = "$packageName.api"

val generateApi by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    generatorName.set("kotlin")
    library.set("multiplatform")
    inputSpec.set("$projectDir/src/commonMain/openapi/flipper-api-swagger.yaml")
    outputDir.set(openapiOutputDir.absolutePath)
    apiPackage.set("$apiPackageName.service")
    modelPackage.set("$apiPackageName.model")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)
    additionalProperties.set(mapOf(
        "dateLibrary" to "kotlinx-datetime",
        "sourceFolder" to "src/commonMain/kotlin"
    ))
}

// Ensure OpenAPI code is generated before any Kotlin compile (JVM, Android, iOS)
tasks.matching { it.name.startsWith("compileKotlin") }.configureEach {
    dependsOn(generateApi)
}
