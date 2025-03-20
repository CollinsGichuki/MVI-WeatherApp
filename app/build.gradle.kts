plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("de.mannodermaus.android-junit5") version "1.12.0.0"
}

android {
    namespace = "com.example.mvi_weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mvi_weatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.accompanist.swiperefresh)

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.55")
    kapt ("com.google.dagger:hilt-compiler:2.55")
//    implementation (libs.hilt.android)
//    kapt (libs.google.hilt.compiler)

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.55")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.55")
//    androidTestImplementation  (libs.google.hilt.android.testing)
//    kaptAndroidTest (libs.google.hilt.compiler)

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.55")
    kaptTest ("com.google.dagger:hilt-compiler:2.55")
//    testImplementation (libs.google.hilt.android.testing)
//    kaptTest (libs.google.hilt.compiler)

    // Location Services
    implementation(libs.play.services.location)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Tests
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("org.robolectric:robolectric:4.6.1")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-core:2.23.0")

    testImplementation("org.amshove.kluent:kluent-android:1.70")
    testImplementation("org.amshove.kluent:kluent:1.70")
}