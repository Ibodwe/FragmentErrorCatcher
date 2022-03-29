package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ErrorActivity : AppCompatActivity() {

    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>(EXTRA_INTENT) }
    private val errorText by lazy { intent.getStringExtra(EXTRA_ERROR_TEXT) }
    private val currentDestination by lazy  { intent.getIntExtra(CURRENT_DESTINATION , -1) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        findViewById<TextView>(R.id.errorTv).setOnClickListener {
            lastActivityIntent?.putExtra(CURRENT_DESTINATION , currentDestination)
            startActivity(lastActivityIntent)
            finish()
        }

    }


    companion object {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"
        const val CURRENT_DESTINATION = "CURRENT_DESTINATION"
    }


}