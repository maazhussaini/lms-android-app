package com.example.orb_ed

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OrbEdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize with your access key (optional) and library ID
//        BunnyStreamApi.initialize(context, accessKey, libraryId)
    }
}

// This is the main application class that will be initialized when the app starts.
// The @HiltAndroidApp annotation triggers Hilt's code generation and sets up the application-level component.
