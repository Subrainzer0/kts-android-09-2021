package ru.shanin.madarareddit

import android.app.Application
import timber.log.Timber

class MadaraRedditApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
