plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.moodnote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moodnote"
        minSdk = 33
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)

    // Dependencies for working with Architecture components
    // You'll probably have to update the version numbers in build.gradle (Project)

    // Room components
    implementation(libs.room.runtime)
    annotationProcessor( libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // Lifecycle components
    implementation(libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.livedata)
    implementation (libs.lifecycle.common.java8)
    implementation(libs.appcompat)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)

    // U
    implementation (libs.constraintlayout)
    implementation (libs.material)
    implementation(libs.room.gradle.plugin)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation("androidx.arch.core:core-testing:$rootProject.coreTestingVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-core:$rootProject.espressoVersion", {
        exclude(group = "com.android.support', module: 'support-annotations")
    })
    androidTestImplementation(libs.ext.junit)
}
