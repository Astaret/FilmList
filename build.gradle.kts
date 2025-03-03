// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.0") 
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.0-1.0.12")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")

    }
}
