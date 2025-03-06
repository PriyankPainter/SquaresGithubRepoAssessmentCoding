package com.example.kotlinapilistdemo.rxjava_sample.constants

import android.app.Application
import android.content.Context

class AppConfig : Application(){

    companion object{
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this
    }

}