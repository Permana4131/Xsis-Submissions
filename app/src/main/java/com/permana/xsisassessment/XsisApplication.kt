package com.permana.xsisassessment

import android.app.Application
import com.permana.xsisassessment.core.di.networkModule
import com.permana.xsisassessment.core.di.repositoryModule
import com.permana.xsisassessment.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class XsisApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@XsisApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                )
            )
        }
    }
}