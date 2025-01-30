plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.fps69.bhagavadgita"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fps69.bhagavadgita"
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
        }
    }


    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // View Dimension

    implementation ("com.intuit.ssp:ssp-android:1.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")


    // Navigation Component
    val nav_version = "2.8.5"

    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // LifeCycle

    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")  // ViewModel with coroutines
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")  // LiveData with coroutines
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")  // Lifecycle-aware components
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")  // Java 8 Lifecycle API


    // RoomDatabase

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
//    kapt ("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:2.5.0")


    // Retrofit

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Shimmer

    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Kotlin Coroutine

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")










}