
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 30
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_API_URL", "\" https://api.themoviedb.org/3/ \"")
        buildConfigField("String", "IMG_API_URL", "\"https://image.tmdb.org/t/p/w500/\"")
    }
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    ksp(libs.bundles.kspRoom)
    ksp(libs.bundles.kspDagger.hiltDependencies)

    debugImplementation(libs.bundles.debugDependencies)
    releaseImplementation(libs.bundles.releaseDependencies)
    androidTestImplementation(libs.bundles.androidTestDependencies)
    testImplementation(libs.bundles.testDependencies)
}