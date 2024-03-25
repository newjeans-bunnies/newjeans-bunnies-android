package newjeans.bunnies.app


import android.app.Application

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NewJeansBunniesApplication : Application() {

    companion object {
        const val TAG = "NewJeansBunniesApplication"
    }

    override fun onCreate() {
        super.onCreate()
    }

}