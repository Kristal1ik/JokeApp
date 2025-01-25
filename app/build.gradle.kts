plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
//    id("com.google.dagger.hilt.android") version "2.51.1"
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}
kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.kristallik.jokeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kristallik.jokeapp"
        minSdk = 24
        targetSdk = 34
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

//    implementation(libs.hilt.android)

    implementation("androidx.work:work-runtime-ktx:2.7.1")

//    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
//    implementation("com.google.dagger:hilt-android:2.43.2")
//
//    implementation("androidx.hilt:hilt-work:1.0.0")
//    ksp("androidx.hilt:hilt-compiler:1.0.0")
//
//    implementation("androidx.work:work-runtime-ktx:2.7.1")
//    implementation("com.google.dagger:dagger:2.51.1")
//
//    implementation("com.google.dagger:dagger-android:2.51.1")
//    implementation("com.google.dagger:hilt-android:2.51.1")
//
//    ksp("com.google.dagger:hilt-compiler:2.51.1")
//    ksp("com.google.dagger:dagger-compiler:2.51.1")
//
//    ksp("com.google.dagger:dagger-android-processor:2.51.1")
//
//    implementation("com.google.dagger:dagger:2.51.1")
//    ksp("com.google.dagger:dagger-compiler:2.51.1")
}
