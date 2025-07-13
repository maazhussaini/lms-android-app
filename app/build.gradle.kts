// Apply plugins with versions from libs.versions.toml
plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.kotlin.android) apply true
    alias(libs.plugins.kotlin.compose) apply true
    
    // Apply Hilt plugin using version catalog
    alias(libs.plugins.hilt.android) apply true
    
    // Apply KSP plugin using version from catalog
    alias(libs.plugins.ksp) apply true
}

android {
    namespace = "com.example.orb_ed"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.orb_ed"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.orb_ed.HiltTestRunner"
        
        // Enable Java 8+ API desugaring support
        multiDexEnabled = true
        
        // Enable vector drawable support
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }
    // Java toolchain is configured in the root project
    
    // Kotlin compiler options
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            // Add any other Kotlin compiler options here
            
            // Enable experimental coroutines API
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-Xjvm-default=all"
            )
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler) {
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    ksp(libs.hilt.compiler) {
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    kspAndroidTest(libs.hilt.android.compiler) {
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    implementation(libs.hilt.navigation.compose)
    
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    
    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen) {
        // Exclude the KAPT annotation processor
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    
    // Room KSP extension
    ksp("androidx.room:room-compiler:${libs.versions.room.get()}") {
        // Exclude the KAPT annotation processor
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    
    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    
    // Coil
    implementation(libs.coil.compose)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// KSP Configuration
androidComponents {
    onVariants { variant ->
        kotlin {
            sourceSets.named(variant.name) {
                kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
            }
        }
    }
}

// Enable Hilt's compiler
hilt {
    enableAggregatingTask = false
}

// KSP is already applied in the plugins block