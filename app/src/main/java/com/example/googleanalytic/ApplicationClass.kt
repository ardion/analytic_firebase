package com.example.googleanalytic

import android.app.Application

class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.initRemoteConfig()
    }
}