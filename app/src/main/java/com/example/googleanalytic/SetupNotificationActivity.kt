package com.example.googleanalytic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.googleanalytic.databinding.ActivitySetupNotificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SetupNotificationActivity : AppCompatActivity() {

  private lateinit var viewBinding: ActivitySetupNotificationBinding

  companion object {
    private const val TOPIC = "awesome_topic"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewBinding = ActivitySetupNotificationBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)
    setupClickListener()
  }

  private fun setupClickListener() {
    with(viewBinding) {
      btnGetToken.setOnClickListener {
        getFirebaseToken()
      }

      btnSubscribeTopic.setOnClickListener {
        subscribeTopic(TOPIC)
      }

      btnUnsubscribeTopic.setOnClickListener {
        unsubscribeTopic(TOPIC)
      }

    }
  }

  private fun getFirebaseToken() {
    Log.d("getFirebaseToken", "masuk")
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
      if (!task.isSuccessful) {
        Log.d("getFirebaseToken", "Fetching FCM registration token failed", task.exception)
        return@OnCompleteListener
      }

      // Get new FCM registration token
      val token = task.result

      // Log and toast
      Log.d("getFirebaseToken", token)
      Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
    })
  }

  private fun subscribeTopic(topic: String) {
    FirebaseMessaging.getInstance().subscribeToTopic(topic)
      .addOnCompleteListener { task ->
        var msg = "Subscribed"
        if (!task.isSuccessful) {
          msg = "Subscribe failed"
        }
        Log.d("subscribeTopic", msg)
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
      }
  }

  private fun unsubscribeTopic(topic: String) {
    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
      .addOnCompleteListener { task ->
        var msg = "Unsubscribed"
        if (!task.isSuccessful) {
          msg = "Unsubscribe failed"
        }
        Log.d("unsubscribeTopic", msg)
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
      }
  }
}