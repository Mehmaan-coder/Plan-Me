plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Consider managing this version via libs.versions.toml if your main kotlin plugin is there
    // For example: kotlin("plugin.serialization") version libs.versions.kotlin.get()
    // However, if "2.1.21" is a specific version needed for serialization and is different
    // from your main Kotlin plugin (libs.versions.kotlin.get()), then this is okay.
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Common general exclusions
            excludes += "META-INF/DEPENDENCIES"    // Specifically exclude the problematic file
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module" // Exclude Kotlin module files if they cause issues
        }
    }
}

dependencies {
    // Jetpack Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // Manages Compose library versions
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // Firebase
    // Update to the latest stable Firebase BoM version
    // Check: https://firebase.google.com/support/release-notes/android#firebase-bom
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Example: Updated BoM
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx") // Use -ktx version for Kotlin extensions
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Google Play Services for Authentication (used for Google Sign-In)
    // Consider adding this to your libs.versions.toml as well
    implementation("com.google.android.gms:play-services-auth:21.0.0") // Or latest version

    // Google Calendar API
    implementation(libs.google.api.services.calendar) // Ensure this is defined in libs.versions.toml

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json) // Ensure this alias points to the correct serialization library

    // Retrofit + Kotlinx Serialization Converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Consider updating to latest stable
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0") // Consider updating
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // For toMediaType(); consider updating

    // Coil for Image Loading
    implementation("io.coil-kt:coil-compose:2.6.0") // Or latest version

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // For Compose testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Extra (if needed, ensure these are still required for your project)
    implementation(libs.transportation.consumer)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.animation.core.lint)
}