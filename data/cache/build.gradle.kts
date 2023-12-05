plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

val compileSdkVer: Int by rootProject.extra
val minimumSdkVer: Int by rootProject.extra

android {
    namespace = "com.example.dailyvita.data.cache"
    compileSdk = compileSdkVer

    defaultConfig {
        minSdk = minimumSdkVer

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":data:common"))
    implementation(project(":domain"))

    implementation(libs.androidx.core)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //koin
    implementation(libs.bundles.koin.android)

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.codegen)

    //Testing
    testImplementation(libs.common.junit)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.mockito.android)
    androidTestImplementation(libs.bundles.mockito)

    testImplementation(libs.bundles.androidx.test)
    testImplementation(libs.test.roboletric)
    androidTestImplementation(libs.bundles.androidx.test)

    //timber for logging
    implementation(libs.common.timber)
}