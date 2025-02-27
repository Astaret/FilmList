plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

android {
    namespace = "com.example.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_API_URL", "\" https://api.themoviedb.org/3/ \"")
        buildConfigField("String", "IMG_API_URL", "\"https://image.tmdb.org/t/p/w500/\"")

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.3"
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
        }

        create("huawei") {
            applicationIdSuffix = ".huawei"
        }
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.bundles.based)
    implementation(libs.bundles.dagger.hiltDependencies)
    ksp(libs.bundles.kspDagger.hiltDependencies)
    implementation(libs.bundles.composeDependencies)
    implementation(libs.glide)
    implementation(libs.serialization.json)
    implementation(libs.bundles.accompanistDependencies)
    implementation(libs.zxing.qrcode)
    implementation(libs.bundles.cameraDependencies)

    debugImplementation(libs.bundles.debugDependencies)
    releaseImplementation(libs.bundles.releaseDependencies)
    androidTestImplementation(libs.bundles.androidTestDependencies)
    testImplementation(libs.bundles.testDependencies)


}

