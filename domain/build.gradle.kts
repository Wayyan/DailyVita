plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

dependencies {
    testImplementation(libs.common.junit)

    testImplementation(libs.bundles.mockito)

    implementation(libs.kotlin.stdlib)
    api(libs.kotlin.coroutine.core)

    implementation(libs.di.koin.core)
    testImplementation(libs.di.koin.test)

    implementation(libs.androidx.annotation)
}