plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("kapt")
    id("com.google.gms.google-services") // Apply this ONCE
    kotlin("plugin.serialization") version libs.versions.kotlin.get() // Apply this ONCE, using the version from your catalog

    // REMOVED DUPLICATES:
    // kotlin("plugin.serialization") version "2.1.21" // Duplicate removed
    // id("com.google.gms.google-services") // Duplicate removed
} // This closing brace for plugins was missing in your original snippet, but I assume it was just a copy-paste error. If not, it's critical.

android {
    namespace = "com.mehmaancoders.planme"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mehmaancoders.planme"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation(libs.firebase.auth.ktx)
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Google Calendar API
    // Consider updating these to their latest versions or managing them with your version catalog for consistency
    implementation("com.google.api-client:google-api-client-android:2.4.0") // Example: updated version
    implementation("com.google.api-client:google-api-client-gson:1.33.2") // This might be older, check if google-http-client-gson is preferred
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.33.2") // Usually not needed for Android client
    implementation("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0") // This version looks okay
    implementation("com.google.http-client:google-http-client-gson:1.44.2") // Use this for GSON with Google HTTP client

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json) // Relies on the version from plugin.serialization

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}