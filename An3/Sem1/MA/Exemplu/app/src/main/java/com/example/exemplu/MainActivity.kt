package com.example.exemplu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logd("oncCreate was reached")
    }

    override fun onStart() {
        super.onStart()
        logd("onStart was reached")
    }

    override fun onResume() {
        super.onResume()
        logd("onResume was reached")
    }

    override fun onPause() {
        super.onPause()
        logd("onPause was reached")
    }

    override fun onStop() {
        super.onStop()
        logd("onStop was reached")
    }

    override fun onRestart() {
        super.onRestart()
        logd("onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        logd("onDestroy called")
    }
}