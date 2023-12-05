plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}


val compileSdkVer: Int by rootProject.extra
val targetSdkVer: Int by rootProject.extra
val minimumSdkVer: Int by rootProject.extra
val versionNameConfig: String by rootProject.extra
val versionCodeConfig: Int by rootProject.extra

android {
    namespace = "com.example.dailyvita"
    compileSdk = compileSdkVer

    defaultConfig {
        applicationId = "com.example.dailyvita"
        minSdk = minimumSdkVer
        targetSdk = targetSdkVer
        versionCode = versionCodeConfig
        versionName = versionNameConfig

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(":base"))

    implementation(libs.androidx.core)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //compose
    implementation(libs.bundles.compose)
    androidTestImplementation(libs.compose.test)
    debugImplementation(libs.bundles.compose.debug)

    // implementation(AndroidXArchLifeCycle.runtime)
    implementation(libs.androidx.work.runtime)


    //koin for di
    implementation(libs.bundles.koin.compose)

    //timber
    implementation(libs.common.timber)

    testImplementation(libs.common.junit)

    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.ext.espresso)
}