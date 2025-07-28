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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        /*maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/BunnyWay/bunny-stream-android")
            credentials {
                // Use environment variables for GitHub Packages authentication
                // Ensure GITHUB_ACTOR and GITHUB_TOKEN are set in your environment
                username = providers.environmentVariable("GITHUB_ACTOR").orNull
                    ?: System.getenv("GITHUB_ACTOR")
                            ?: ""
                password = providers.environmentVariable("GITHUB_TOKEN").orNull
                    ?: System.getenv("GITHUB_TOKEN")
                            ?: ""
            }
        }*/
    }
}

rootProject.name = "Orb-Ed"
include(":app")
 