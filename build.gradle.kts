// Top-level build file where you can add configuration options common to all sub-projects/modules.
val compileSdkVer by extra { 34 }
val targetSdkVer by extra { 34 }
val minimumSdkVer by extra { 21 }

private val versionMajor = 1
private val versionMinor = 0
private val versionPatch = 0
private val versionBuild = 0
val versionNameConfig by extra { "$versionMajor.$versionMinor.$versionPatch.$versionBuild" }
val versionCodeConfig by extra { versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild * 10 }

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.ksp) apply false
}