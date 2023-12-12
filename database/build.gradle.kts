@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("realm-android")
}

android {
    namespace = "newjeans.bunnies.database"
    compileSdk = 33

    defaultConfig {
        applicationId = "newjeans.bunnies"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    dependencies {
        implementation (libs.library.base.v1110)
        implementation (libs.library.sync.v1110)// If using Device Sync
        implementation (libs.coroutines.core) // If using coroutines with the SDK
    }
}