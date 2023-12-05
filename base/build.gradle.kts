@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val compileSdkVer: Int by rootProject.extra
val minimumSdkVer: Int by rootProject.extra

android {
    namespace = "com.example.dailyvita.base"
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
    api(project(":domain"))
    api(project(":data:common"))
    api(project(":data:cache"))

    implementation(libs.androidx.core)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //Timber
    implementation(libs.common.timber)

    //di
    implementation(libs.bundles.koin.android)

    //Testing
    testImplementation(libs.common.junit)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.mockito.android)
    androidTestImplementation(libs.bundles.mockito)

    testImplementation(libs.bundles.androidx.test)
    testImplementation(libs.test.roboletric)
    androidTestImplementation(libs.bundles.androidx.test)
}