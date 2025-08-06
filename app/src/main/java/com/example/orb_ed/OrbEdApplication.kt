package com.example.orb_ed

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import com.example.orb_ed.util.Constants.ACCESS_KEY
import com.example.orb_ed.util.Constants.LIBRARY_ID
import dagger.hilt.android.HiltAndroidApp
import net.bunnystream.api.BunnyStreamApi

@HiltAndroidApp
class OrbEdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
        // Initialize with your access key (optional) and library ID
        BunnyStreamApi.initialize(
            applicationContext,
            ACCESS_KEY,
            LIBRARY_ID
        )
    }
}

// This is the main application class that will be initialized when the app starts.
// The @HiltAndroidApp annotation triggers Hilt's code generation and sets up the application-level component.
