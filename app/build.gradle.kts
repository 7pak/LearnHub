plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("kotlinx-serialization")
    id ("com.google.devtools.ksp")
    id("de.jensklingenberg.ktorfit") version "1.0.0"
    id("kotlin-parcelize")
}

val ktorfit = "1.6.0"

configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
    version = ktorfit
}


android {
    namespace = "com.example.courseapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.courseapp"
        minSdk = 24
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.ui:ui:1.4.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation( "androidx.compose.material:material:1.4.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.4.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.4.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.4.0")

    implementation( "androidx.compose.material:material-icons-extended:1.4.3")

    val lifecycle_version = "2.6.1"
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    //life cycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")



    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    val ktor_version = "2.3.1"
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfit")
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfit")


    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.11")
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib-light:1.6.0")

    // GSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    //System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha")
    //Data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //swipe
    implementation("me.saket.swipe:swipe:1.1.1")
    //exoplayer

    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")

}