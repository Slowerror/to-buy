package com.slowerror.tobuy

import android.app.Application
import com.slowerror.tobuy.data.local.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(applicationContext)
    }
}