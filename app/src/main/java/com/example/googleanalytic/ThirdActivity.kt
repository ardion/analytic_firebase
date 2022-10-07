package com.example.googleanalytic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googleanalytic.databinding.ActivityFirstBinding
import com.example.googleanalytic.databinding.ActivityMainBinding
import com.example.googleanalytic.databinding.ActivityThirdBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class ThirdActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var viewBinding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityThirdBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
        firebaseAnalytics = Firebase.analytics
        recordScreenView()
    }

    private fun recordScreenView() {
        val screenName = "Third Page"
        firebaseAnalytics.logEvent("record_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "ThirdActivity")
        }
    }

//    override fun onResume() {
//        super.onResume()
//        recordScreenView()
//    }

    fun coba(){
        
    }
}