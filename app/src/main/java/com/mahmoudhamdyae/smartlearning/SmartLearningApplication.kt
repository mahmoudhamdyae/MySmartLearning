package com.mahmoudhamdyae.smartlearning

import android.app.Application
import com.google.android.material.color.DynamicColors

class SmartLearningApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Apply dynamic color
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}