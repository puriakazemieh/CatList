plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.cat.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
        unitTests {
            isIncludeAndroidResources= true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    // Coil
    implementation(libs.landscapist.coil3)
    implementation(libs.landscapist.placeholder)

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
    // Koin Test
    testImplementation(libs.koin.test)
}