plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bangkit.befitoutfit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bangkit.befitoutfit"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
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
                getDefaultProguardFile(
                    "proguard-android-optimize.txt",
                ),
                "proguard-rules.pro",
            )
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
        buildConfig = true
        compose = true
        mlModelBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // default
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // camera
    val cameraVersion = "1.3.1"
    implementation("androidx.camera:camera-core:$cameraVersion")
    implementation("androidx.camera:camera-camera2:$cameraVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraVersion")
    implementation("androidx.camera:camera-video:$cameraVersion")
    implementation("androidx.camera:camera-view:$cameraVersion")
    implementation("androidx.camera:camera-extensions:$cameraVersion")

    // coil-compose
    val coilComposeVersion = "2.5.0"
    implementation("io.coil-kt:coil-compose:$coilComposeVersion")

    // converter-gson
    val converterGsonVersion = "2.9.0"
    implementation("com.squareup.retrofit2:converter-gson:$converterGsonVersion")

    // core-splashscreen
    val coreSplashscreenVersion = "1.0.1"
    implementation("androidx.core:core-splashscreen:$coreSplashscreenVersion")

    // datastore-preferences
    val datastorePreferencesVersion = "1.0.0"
    implementation("androidx.datastore:datastore-preferences:$datastorePreferencesVersion")

    // insert-koin
    val insertKoinVersion = "3.5.0"
    implementation("io.insert-koin:koin-androidx-compose:$insertKoinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$insertKoinVersion")

    // lifecycle-viewmodel-compose
    val lifecycleViewmodelComposeVersion = "2.7.0-rc02"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleViewmodelComposeVersion")

    // logging-interceptor
    val loggingInterceptorVersion = "5.0.0-alpha.12"
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")

    // material
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")

    // navigation
    val navigationVersion = "2.7.6"
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")

    // tensorflow
    val tensorflowVersion = "2.14.0"
    implementation("org.tensorflow:tensorflow-lite:$tensorflowVersion")
    implementation("org.tensorflow:tensorflow-lite-gpu:$tensorflowVersion")

    // tensorflow-lite
    val tensorflowLiteVersion = "0.4.4"
    implementation("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:$tensorflowLiteVersion")
    implementation("org.tensorflow:tensorflow-lite-metadata:$tensorflowLiteVersion")
    implementation("org.tensorflow:tensorflow-lite-support:$tensorflowLiteVersion")
    implementation("org.tensorflow:tensorflow-lite-task-vision:$tensorflowLiteVersion")
}
