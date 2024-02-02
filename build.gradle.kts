@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.google.services) apply false
    id("com.google.gms.google-services") version "4.3.8" apply false

//    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
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
//        classpath(libs.realm.gradle)
//        classpath(libs.realm.transformer)
    }
}

