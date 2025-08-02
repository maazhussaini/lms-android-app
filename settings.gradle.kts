pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        // Add KSP repository
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            content {
                includeGroupByRegex("com\\.google\\.devtools\\.ksp")
            }
        }
    }
}

dependencyResolutionManagement {
    // Use PREFER_SETTINGS to prioritize settings repositories over project repositories
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/BunnyWay/bunny-stream-android")
            credentials {
                // Use environment variables for GitHub Packages authentication
                // Ensure GITHUB_ACTOR and GITHUB_TOKEN are set in your environment
                username = providers.gradleProperty("gpr.user").orNull
                    ?: providers.environmentVariable("GITHUB_ACTOR").orNull
                            ?: System.getenv("GITHUB_ACTOR")
                            ?: "AhmadMeghani"
                print("Bunny username: $username")

                password = providers.gradleProperty("gpr.key").orNull
                    ?: providers.environmentVariable("GITHUB_TOKEN").orNull
                            ?: System.getenv("GITHUB_TOKEN")
                            ?: "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"
                print("Bunny password: $password")
            }
        }
    }
}

rootProject.name = "Orb-Ed"
include(":app")
 