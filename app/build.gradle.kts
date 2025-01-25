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
        unitTests.all {
            it.useJUnitPlatform()
        }
        unitTests {
            isIncludeAndroidResources= true
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
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
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
//    implementation( "io.insert-koin:koin-android:3.4.0")

    // Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)


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
    // Test coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // Test junit 5
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    // Test Mocking
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)
    androidTestImplementation(libs.mockk.android)
    // Test ui
    androidTestImplementation(libs.androidx.ui.test.junit4.v151)
    debugImplementation(libs.ui.test.manifest)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    // Koin Test
    testImplementation (libs.koin.test)


}