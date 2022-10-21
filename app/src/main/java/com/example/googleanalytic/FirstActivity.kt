package com.example.googleanalytic

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.googleanalytic.databinding.ActivityFirstBinding
import com.example.googleanalytic.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class FirstActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var viewBinding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFirstBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        firebaseAnalytics = Firebase.analytics

        val bundle = intent.extras
        Log.d("FirstActivity", bundle?.get("type").toString())

        recordScreenView()
        onClickListener()

        viewBinding.btnNext.apply {
            text=RemoteConfigUtils.getButtonText()
            setBackgroundColor(Color.parseColor(RemoteConfigUtils.getButtonColor()))
        }
    }

    private fun recordScreenView2() {
        val screenName = "First Page"

        firebaseAnalytics.logEvent("record_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "First Page3")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "FirstActivity")
        }
    }

    private fun recordScreenView() {
        val screenName = "First Page"

        firebaseAnalytics.logEvent("record_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "First Page5")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "FirstActivity")
        }
    }

    private fun onClickListener() {
        with(viewBinding) {
            btnNext.setOnClickListener {
                firebaseAnalytics.setUserProperty("profession","worker")
                startActivity(Intent(this@FirstActivity, MainActivity::class.java))
            }

            btnNotif.setOnClickListener {
                startActivity(Intent(this@FirstActivity, SetupNotificationActivity::class.java))
            }

            imageFirst.setOnClickListener {
                recordSelectItemImage("1", "image lion")
                imageSecond.visibility = View.GONE
                imageSecond.isClickable=false
                btnNext.isEnabled=true
            }

            imageSecond.setOnClickListener {
                recordSelectItemImage("2", "image cat")
                imageFirst.visibility = View.GONE
                imageFirst.isClickable=false
                btnNext.isEnabled=true
            }
        }
    }



    private fun recordSelectItemImage(id: String, name: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
            param(FirebaseAnalytics.Param.ITEM_NAME, name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param("screen_name", "First Page3")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "FirstActivity")
        }
    }
}