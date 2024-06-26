plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

android {
    namespace = "com.example.money_splitter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.money_splitter"
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
            excludes += "/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES}"
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation("org.testng:testng:6.9.6") {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    androidTestImplementation(libs.androidx.junit) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    androidTestImplementation(libs.androidx.espresso.core) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    androidTestImplementation(platform(libs.androidx.compose.bom)) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    androidTestImplementation(libs.androidx.ui.test.junit4) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    debugImplementation(libs.androidx.ui.tooling) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    debugImplementation(libs.androidx.ui.test.manifest) {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }

    // ROOM
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // TEST
    // Unit testing dependencies
    testImplementation("junit:junit:4.12") {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    // Set this dependency if you want to use the Hamcrest matcher library
    testImplementation("org.hamcrest:hamcrest-library:1.3") {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }
    // To use the androidx.test.core APIs
    androidTestImplementation("androidx.test:core:1.5.0")
    // Kotlin extensions for androidx.test.core
    androidTestImplementation("androidx.test:core-ktx:1.5.0")

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")

    // To use the Truth Extension APIs
    androidTestImplementation("androidx.test.ext:truth:1.5.0")

    // To use the androidx.test.runner APIs
    androidTestImplementation("androidx.test:runner:1.5.2")

    // To use android test orchestrator
    androidTestUtil("androidx.test:orchestrator:1.4.2")
}

configurations.all {
    resolutionStrategy {
        // Forcer les versions spécifiques pour éviter les conflits
        force("com.google.guava:guava:30.1-jre")
        force("org.hamcrest:hamcrest-core:1.3")
    }
}
