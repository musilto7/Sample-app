import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.openapi.generator")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version libs.versions.kotlin
    alias(libs.plugins.compose.compiler)
}

val openapiOutputDir = layout.buildDirectory.asFile.get().resolve("generated/openapi")

android {
    namespace = "cz.musilto5.myflickerapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "cz.musilto5.myflickerapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("$openapiOutputDir/src/commonMain/kotlin")
        }
    }
}


dependencies {

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    coreLibraryDesugaring(libs.core.library.desugaring)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

val packageName = "cz.musilto5.myflickerapp.data"

val apiPackageName = "$packageName.api"
val generateApi by tasks.registering(org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    generatorName.set("kotlin")
    library.set("multiplatform")
    inputSpec.set("$projectDir/src/main/openapi/flipper-api-swagger.yaml")
    outputDir.set(openapiOutputDir.path)
    apiPackage.set("$apiPackageName.service")
    modelPackage.set("$apiPackageName.model")

    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)
    // Add additional properties
    additionalProperties.set(mapOf("dateLibrary" to "kotlinx-datetime"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn(generateApi)

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
