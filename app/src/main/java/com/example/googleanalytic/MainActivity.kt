package com.example.googleanalytic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytic.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        firebaseAnalytics = Firebase.analytics

        recordScreenView()

        viewBinding.btn1.setOnClickListener {
            recordCustomEvent("button 1")
            navigate()
        }
        viewBinding.btn1.text=RemoteConfigUtils.getButtonText()

        viewBinding.btn2.setOnClickListener {
            recordCustomEvent("button 2")
            navigate()
        }

        viewBinding.btn3.setOnClickListener {
            recordCustomEvent("button 3")
            navigate()
        }

    }

    private fun recordScreenView() {
        val screenName = "Second Pagedd"

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "SecondActivity")
        }
    }

    private fun recordCustomEvent(name:String){
        firebaseAnalytics.logEvent("select_button") {
            param("button_name", name)
        }
    }

    private fun navigate(){
        startActivity(Intent(this,ThirdActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        recordScreenView()
    }
}