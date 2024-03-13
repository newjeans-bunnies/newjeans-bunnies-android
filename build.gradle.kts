@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.dependency.graph.generator.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}

