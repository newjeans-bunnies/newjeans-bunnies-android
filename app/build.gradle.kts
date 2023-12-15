@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "newjeans.bunnies"
    compileSdkVersion = "android-34"


    defaultConfig {
        applicationId = "newjeans.bunnies"
        versionCode = 1
        versionName = "1.0"
        minSdk = 26

    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        implementation(libs.androidx.core.splashscreen)

        constraints {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.21") {
                because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
            }
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21") {
                because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
            }
        }

        implementation(project(":auth"))
        implementation(project(":database"))
    }
}