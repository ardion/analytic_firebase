package com.example.googleanalytic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService()  {

  companion object {
    private const val CHANNEL_ID = "my_tens_next_channel"
  }

  override fun onNewToken(token: String) {
    Log.d("NEW_TOKEN", "Refreshed token: $token")
  }

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onMessageReceived(message: RemoteMessage) {
    val title = message.notification?.title
    val body = message.notification?.body

    val type = message.data["type"] ?: ""
    val id = message.data["id"] ?: ""

    Log.d("getIntentByType_data", message.data.toString())

    val timeStamp = System.currentTimeMillis()
    createNotificationChannel()

    val notifyIntent = getIntentByType(type)
    val notifyPendingIntent = wrapTaskStack(notifyIntent).getPendingIntent(
      0,
      PendingIntent.FLAG_IMMUTABLE
    )

    val builder: NotificationCompat.Builder = NotificationCompat.Builder(
      this,
      CHANNEL_ID
    )
      .setSmallIcon(R.drawable.ic_notif)
      .setContentTitle(title)
      .setContentText(body)
      .setColor(ContextCompat.getColor(this, R.color.purple_200))
      .setContentIntent(notifyPendingIntent)
      .setAutoCancel(true)
    NotificationManagerCompat.from(this).notify(
      timeStamp.toInt(),
      builder.build()
    )

    super.onMessageReceived(message)
  }

  private fun createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "tens_channel"
      val descriptionText = "this is my tens channel"

      // other Importance types are
      // IMPORTANCE_HIGH
      // IMPORTANCE_LOW
      // IMPORTANCE_MAX
      // IMPORTANCE_MIN
      val importance = NotificationManager.IMPORTANCE_HIGH

      // define your own channel code here i used a predefined constant
      val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
      }

      // Register the channel with the system
      // I am using application class's context here
      val notificationManager: NotificationManager =
        application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }

  private fun getIntentByType(type: String): Intent? {
    Log.d("getIntentByType", type)
    return when (type) {
      "first" -> {
        Intent(this, FirstActivity::class.java)
      }
      "second" -> {
        Intent(this, MainActivity::class.java)
      }
      "third" -> {
        Intent(this, ThirdActivity::class.java)
      }
      else -> {
        null
      }
    }
  }

  private fun wrapTaskStack(intent: Intent?): TaskStackBuilder {
    val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
    stackBuilder.addNextIntentWithParentStack(Intent(this, FirstActivity::class.java))
    intent?.let { stackBuilder.addNextIntent(it) }
    return stackBuilder
  }

}