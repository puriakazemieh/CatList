plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.cat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cat"
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
    testOptions {
        unitTests.all { it.useJUnitPlatform() }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose.android)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Koin
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.compose)

    // Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)


    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil)
    implementation(libs.landscapist.coil3)
    implementation(libs.landscapist.placeholder)

    // database
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    // Test
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter.engine)
    // Mocking
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)


//    implementation(libs.androidx.core)
//    testImplementation(libs.androidx.core)
//    testImplementation(libs.androidx.runner)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)

//    testImplementation (libs.junit)
//    testImplementation (libs.mockito.core)
//    testImplementation (libs.mockito.kotlin)
//    testImplementation (libs.androidx.core.testing)
//    testImplementation (libs.kotlin.test)


    // Compose Testing
//    androidTestImplementation( libs.ui.test.junit4)

    // Paging Testing
//    testImplementation (libs.androidx.paging.common.ktx)

//    testImplementation (libs.junit.jupiter.api)
//    testRuntimeOnly( libs.junit.jupiter.engine)
//    testImplementation( libs.junit.jupiter.params)

    // Koin Test
//    testImplementation ("io.insert-koin:koin-test:3.4.0")
//    androidTestImplementation ("io.insert-koin:koin-android-test:3.4.0")


    // Test
//    testImplementation ("com.willowtreeapps.assertk:assertk:0.26.1")
//    testImplementation ("androidx.room:room-testing:2.4.2")
//    testImplementation ("androidx.paging:paging-testing:3.1.1")
}