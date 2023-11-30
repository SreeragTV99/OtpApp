plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.otpapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.otpapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation ("net.time4j:time4j-android:4.5-2022e")
//    implementation ("net.time4j:time4j-android-extensions:4.5-2022e")
//    implementation ("net.time4j:time4j-android-plugin:4.5-2022e")
//    implementation ("net.time4j:time4j-android-calendar:4.5-2022e")
//    implementation ("net.time4j:time4j-android-timezone:4.5-2022e")
//    implementation ("net.time4j:time4j-android-tzdata:4.5-2022e")
//    implementation ("net.time4j:time4j-android-tzdb:4.5-2022e")
}