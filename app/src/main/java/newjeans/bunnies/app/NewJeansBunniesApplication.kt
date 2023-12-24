package newjeans.bunnies.app


import android.app.Application

import dagger.hilt.android.HiltAndroidApp

import newjeans.bunnies.data.PreferenceManager


@HiltAndroidApp
class NewJeansBunniesApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate() {
        prefs = PreferenceManager(applicationContext)
        super.onCreate()
    }

}