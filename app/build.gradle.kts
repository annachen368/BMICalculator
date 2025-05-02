plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp) // Required for annotation processing
}

android {
    namespace = "io.github.annachen368.bmicalculator"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.annachen368.bmicalculator"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android) // Hilt dependency
    ksp(libs.hilt.compiler) // Hilt compiler
    implementation(libs.hilt.navigation.compose) // If using Jetpack Compose
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson) // JSON converter
    implementation(libs.okhttp.logging) // Optional: Logging Interceptor
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata) // Optional for LiveData
    implementation(libs.lifecycle.runtime) // Optional for Coroutine support
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.coil.compose) // Coroutine Image Loader
    implementation(libs.coil.network) // Coroutine Image Loader

    testImplementation(libs.junit)
    testImplementation(libs.room.testing) // Optional - Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}