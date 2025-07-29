import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Apply plugins with versions from libs.versions.toml
plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.kotlin.android) apply true
    alias(libs.plugins.kotlin.compose) apply true
    alias(libs.plugins.kotlin.serialization) apply true
    alias(libs.plugins.hilt.android) apply true
    alias(libs.plugins.ksp) apply true
}

android {
    namespace = "com.example.orb_ed"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.orb_ed"
        minSdk = 26
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
    // Force specific versions for dependencies with conflicts
    configurations.all {
        resolutionStrategy {
            // Force specific versions for known conflicts
            force("com.squareup:javapoet:1.13.0")

            // Ensure all Dagger dependencies use the same version
            eachDependency {
                if (requested.group == "com.google.dagger") {
                    // Use the version from libs.versions.toml for all Dagger dependencies
                    useVersion(libs.versions.hiltAndroid.get())
                }
            }
        }
    }

    // Explicitly add JavaPoet
    implementation("com.squareup:javapoet:1.13.0")

    // Error Prone annotations (required by Tink)
    implementation("com.google.errorprone:error_prone_annotations:2.23.0")

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Security
    implementation(libs.androidx.security.crypto.ktx)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.common.ktx)

    implementation("com.exyte:animated-navigation-bar:1.0.0")

    /*implementation("net.bunnystream:api:1.0.0")
    implementation("net.bunnystream:player:1.0.0")
    implementation("net.bunnystream:recording:1.0.0")*/
    
    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

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

    // Retrofit with Kotlin Serialization
    implementation(libs.retrofit) {
        exclude(module = "converter-gson")  // Exclude Gson converter
    }
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    
    // Room
//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    ksp(libs.room.compiler)
    
    // Room KSP extension
    ksp("androidx.room:room-compiler:${libs.versions.room.get()}") {
        exclude("org.jetbrains.kotlin:kotlin-annotation-processing")
    }
    
    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    
    // Coil
    implementation(libs.coil.compose)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Security Crypto
    implementation(libs.androidx.security.crypto)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    
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

// KSP Configuration
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

// Enable Java 8+ API desugaring
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
        freeCompilerArgs += listOf(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }

    // Ensure consistent JVM target for all compilation tasks
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    // Configure Java compilation to match Kotlin's JVM target
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }
}

// Add core library desugaring
dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}