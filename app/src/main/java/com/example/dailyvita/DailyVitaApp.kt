package com.example.dailyvita

import android.app.Application
import com.example.dailyvita.base.di.BaseAppModule
import com.example.dailyvita.data.cache.di.CacheModule
import com.example.dailyvita.data.common.di.DataModule
import com.example.dailyvita.di.AppModule
import com.example.dailyvita.domain.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class DailyVitaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@DailyVitaApp)

            modules(
                listOf(
                    DomainModule,
                    DataModule,
                    CacheModule,
                    BaseAppModule,
                    AppModule
                )
            )
        }
    }
}