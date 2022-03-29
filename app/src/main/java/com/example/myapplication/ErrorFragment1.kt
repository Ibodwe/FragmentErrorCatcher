package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class ErrorFragment1 : Fragment(R.layout.fragment_error_1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.errorFragment).setOnClickListener {
            throw RuntimeException("Test Crash")
        }

    }
}