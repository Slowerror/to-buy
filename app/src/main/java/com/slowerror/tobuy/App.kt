package com.slowerror.tobuy

import android.app.Application
import com.slowerror.tobuy.data.repository.di.appModule
import com.slowerror.tobuy.data.repository.di.dataModule
import com.slowerror.tobuy.data.repository.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
        }
    }
}