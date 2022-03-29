package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.ErrorActivity.Companion.CURRENT_DESTINATION

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)

        val lastDestination = intent.getIntExtra(CURRENT_DESTINATION , -1)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment 
        
        val navController = navHostFragment.navController

        // navcontroller를 set 하냐 안하냐의 문제

        if(lastDestination != -1) {

            Log.d("currentDestinationId77" , lastDestination.toString())

            val findFragment = navController.findDestination(lastDestination)

            Log.d("currentDestinationId66" , findFragment?.id.toString())

            findFragment?.id?.let { navController.navigate(it) }

        }


        findViewById<Button>(R.id.error1).setOnClickListener {
            navController.navigate(R.id.errorFragment1)

        }

        findViewById<Button>(R.id.error2).setOnClickListener {

            navController.navigate(R.id.errorFragment2)
        }

    }


}