package com.qu3dena.lawconnect.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.util.Log

@HiltAndroidApp
class LawConnectApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        setupGlobalErrorHandler()
    }
    
    private fun setupGlobalErrorHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("LawConnectApp", "Uncaught exception in thread: ${thread.name}", throwable)
            
            // Log additional information about the error
            Log.e("LawConnectApp", "Error details: ${throwable.message}")
            Log.e("LawConnectApp", "Error type: ${throwable.javaClass.simpleName}")
            
            // Call the default handler to maintain normal crash behavior
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}