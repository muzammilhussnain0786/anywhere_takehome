@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.anywhere.takehome"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.anywhere.takehome"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.anywhere.takehome.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Enable room auto-migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        aidl = false
        buildConfig = true
        renderScript = true
        shaders = true
        viewBinding = true
        dataBinding = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/*"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    flavorDimensions += "characters"
    productFlavors {
        create("simpsons") {
            applicationId = "com.sample.simpsonsviewer"
            resValue("string", "app_name", "Simpsons Character Viewer")
            buildConfigField("String", "QUERY", "\"Simpsons Characters\"")
            buildConfigField("String", "BASE_URL", "\"https://duckduckgo.com\"")
            buildConfigField("String", "BASE_API_URL", "\"https://api.duckduckgo.com\"")

        }
        create("thewire") {
            applicationId = "com.sample.wireviewer"
            resValue("string", "app_name", "The Wire Character Viewer")
            buildConfigField("String", "QUERY", "\"The Wire Characters\"")
            buildConfigField("String", "BASE_URL", "\"https://duckduckgo.com\"")
            buildConfigField("String", "BASE_API_URL", "\"https://api.duckduckgo.com\"")

        }
    }

    }

dependencies {

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx )

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Arch Components
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.arch.core.common)
    implementation(libs.arch.core.runtime)
    testImplementation(libs.arch.core.testing)
    androidTestImplementation(libs.arch.core.testing)
    implementation(libs.androidx.slidingpanelayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.window)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.recyclerview.selection)
    implementation(libs.androidx.fragment.testing)
    implementation(libs.androidx.cardview)

    //Image loading
    implementation(libs.glide)

    //Network
    implementation(libs.network.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.converter)

    // Instrumented tests
    testImplementation(libs.robolectric)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests: jUnit rules and runners

    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.test.runner)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)

    // mock webserver
    testImplementation(libs.mock.webserver)
    androidTestImplementation(libs.mock.webserver)

    //mockk
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
}
