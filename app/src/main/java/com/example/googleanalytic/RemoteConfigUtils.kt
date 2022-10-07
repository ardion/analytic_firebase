package com.example.googleanalytic

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {
    private const val TAG = "RemoteConfigUtils"
    private const val BUTTON_TEXT = "button_text"
    private const val BUTTON_COLOR = "button_color"

    private val DEFAULT: HashMap<String, Any> =
        hashMapOf(
            BUTTON_TEXT to "submit",
            BUTTON_COLOR to "#FF9800"
        )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun initRemoteConfig() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

        val configSetting: FirebaseRemoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0
            } else {
                3600
            }
        }

        remoteConfig.apply {
            setConfigSettingsAsync(configSetting)
            setDefaultsAsync(DEFAULT)
            fetchAndActivate().addOnCompleteListener {
            }
        }
        return remoteConfig
    }

    fun getButtonText():String{
        return remoteConfig.getString(BUTTON_TEXT)
    }
    fun getButtonColor():String = remoteConfig.getString(BUTTON_COLOR)

}