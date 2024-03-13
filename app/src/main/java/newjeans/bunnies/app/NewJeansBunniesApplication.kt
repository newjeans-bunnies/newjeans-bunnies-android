package newjeans.bunnies.app


import android.app.Application

import dagger.hilt.android.HiltAndroidApp

import newjeans.bunnies.data.PreferenceManager

@HiltAndroidApp
class NewJeansBunniesApplication : Application() {

    companion object {
        const val TAG = "NewJeansBunniesApplication"
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager(applicationContext)
    }

}