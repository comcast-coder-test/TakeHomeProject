package com.noahseidman.comcast

import android.app.Application

class Application : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this;
        Networking.instance.init();
    }
}