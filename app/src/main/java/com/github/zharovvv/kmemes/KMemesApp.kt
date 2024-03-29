package com.github.zharovvv.kmemes

import android.app.Application
import com.github.zharovvv.kmemes.di.core.coreModule
import com.github.zharovvv.kmemes.di.data.dataModule
import com.github.zharovvv.kmemes.di.domain.domainModule
import com.github.zharovvv.kmemes.di.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KMemesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@KMemesApp)
            modules(
                listOf(
                    coreModule,
                    dataModule,
                    domainModule,
                    uiModule
                )
            )
        }
    }
}