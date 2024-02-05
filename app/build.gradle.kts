@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "newjeans.bunnies"
    compileSdk = 34


    defaultConfig {
        applicationId = "newjeans.bunnies"
        versionCode = 1
        versionName = "1.0"
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }


    dependencies {
        implementation(project(":designsystem"))
        implementation(project(":database"))
        implementation(project(":network"))
        implementation(project(":auth"))
        implementation(project(":main"))
        implementation(project(":di"))

        constraints {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.21") {
                because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
            }
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21") {
                because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
            }
        }

        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.navigation)

        debugImplementation(libs.androidx.ui.tooling)

        implementation(libs.androidx.compose.ui.tooling.preview)
        debugImplementation(libs.androidx.compose.ui.tooling.preview)

        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)



        implementation(libs.androidx.material)

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
    }
}