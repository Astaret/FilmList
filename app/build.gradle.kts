plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android") version ("2.48") apply false
}

android {
    namespace = "com.example.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation ("com.squareup.picasso:picasso:2.8")

    implementation ("com.squareup.retrofit2:retrofit:2.10.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.10.0")

    implementation("com.google.dagger:dagger:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48")

    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")

    testImplementation("junit:junit:4.13.2")

    // Для инструментальных тестов
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
