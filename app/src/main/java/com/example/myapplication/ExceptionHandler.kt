package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.navigation.findNavController
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class ExceptionHandler(
    application: Application,
    private val crashlyticsExceptionHandler : Thread.UncaughtExceptionHandler
) : Thread.UncaughtExceptionHandler {

    private var lastActivity : Activity? = null
    private var activityCount = 0

    init {

        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, p1: Bundle?) {

                    if(isSkipActivity(activity)) { return }

                    lastActivity = activity

                }

                override fun onActivityDestroyed(activity: Activity) {

                }

                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {

                }

                override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {

                }

                override fun onActivityStarted(activity: Activity) {
                    if(isSkipActivity(activity)) {
                        return
                    }

                    activityCount++
                    lastActivity = activity

                }

                override fun onActivityStopped(activity: Activity) {
                    if (isSkipActivity(activity)) {
                        return
                    }

                    activityCount--

                    if (activityCount < 0) {
                        lastActivity = null
                    }

                }
            }
        )


    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {

        lastActivity?.run {

            val stringWriter = StringWriter()

            throwable.printStackTrace(PrintWriter(stringWriter))

            val currentDestination = this.findNavController(R.id.container).currentDestination?.id

            startErrorActivity(this , stringWriter.toString() , currentDestination)

        }

        crashlyticsExceptionHandler.uncaughtException(thread , throwable)

        Process.killProcess(Process.myPid())
        exitProcess(-1)
    }


    private fun isSkipActivity(activity: Activity) = activity is ErrorActivity

    private fun startErrorActivity(activity: Activity ,
                                   errorText : String,
                                   destinationId : Int?) = activity.run {
        val errorActivityIntent = Intent(this , ErrorActivity::class.java).apply {
            putExtra(ErrorActivity.EXTRA_INTENT , intent)
            putExtra(ErrorActivity.EXTRA_ERROR_TEXT , errorText)
            destinationId?.let {  putExtra(ErrorActivity.CURRENT_DESTINATION , it)  }
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(errorActivityIntent)
        finish()
    }

}