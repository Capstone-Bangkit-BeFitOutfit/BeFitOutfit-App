plugins {
    id("com.android.application")
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
        vectorDrawables { useSupportLibrary = true }

        buildConfigField(
            "Boolean",
            "DEBUG",
            "true",
        )
        buildConfigField(
            "Boolean",
            "MOCK",
            "false",
        )
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://befitoutfit.et.r.appspot.com/\"",
        )
        buildConfigField(
            "String",
            "BASE_URL_MOCK",
            "\"https://be25263b-a3c2-4fcc-a653-af5e0e436790.mock.pstmn.io\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { jvmTarget = "1.8" }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.4" }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
    // default
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
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

    // camerax
    val cameraxVersion = "1.3.0"
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")

    // coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // koin
    val koinVersion = "3.5.0"
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")

    // logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // material
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")

    // navigation
    val navigationVersion = "2.7.5"
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")

    // retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
}
