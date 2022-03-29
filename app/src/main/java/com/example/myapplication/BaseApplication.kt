package com.example.myapplication

import android.app.Application

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setCrashHandler()
    }

    private fun setCrashHandler() {

        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return

        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this , crashlyticsExceptionHandler))

    }
}