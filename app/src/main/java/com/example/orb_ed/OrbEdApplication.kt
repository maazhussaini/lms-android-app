package com.example.orb_ed

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.bunnystream.api.BunnyStreamApi

@HiltAndroidApp
class OrbEdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize with your access key (optional) and library ID
        BunnyStreamApi.initialize(
            applicationContext,
            "cebff89b-1357-4016-8cf1ede98059-5f31-465a",
            459051
        )
    }
}

// This is the main application class that will be initialized when the app starts.
// The @HiltAndroidApp annotation triggers Hilt's code generation and sets up the application-level component.
