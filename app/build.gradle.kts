plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32

    defaultConfig.apply {
        applicationId = "com.github.zharovvv.kmemes"
        minSdk = 23
        targetSdk = 32
        buildToolsVersion = "32.0.0"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.bundles.core)
    implementation(libs.bundles.jetpack.compose)
    implementation(libs.coroutines)
    implementation(libs.bundles.koin)
    debugImplementation(libs.bundles.jetpack.compose.debug)
    testImplementation(libs.bundles.test)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(libs.androidTest.compose)
}