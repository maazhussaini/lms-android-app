// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Hilt and KSP plugins are now applied in the app's build.gradle.kts
}

// Configure all projects
allprojects {
    // Configure Java toolchain for all projects that apply the Java plugin
    plugins.withType<JavaBasePlugin>().configureEach {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
        }
    }
    
    // Configure Kotlin compilation for all projects that apply the Kotlin plugin
    plugins.withId("org.jetbrains.kotlin.android") {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
                // Enable experimental coroutines API
                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            }
        }
        
        // Configure Java compatibility
        tasks.withType<JavaCompile>().configureEach {
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}