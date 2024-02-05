package newjeans.bunnies.app


import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.Log

import dagger.hilt.android.HiltAndroidApp
import newjeans.bunnies.auth.AuthActivity

import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity


@HiltAndroidApp
class NewJeansBunniesApplication : Application() {

    private lateinit var authIntent: Intent
    private lateinit var mainIntent: Intent
    private lateinit var splashIntent: Intent

    companion object {
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate() {
        prefs = PreferenceManager(applicationContext)
        super.onCreate()

        // Initialize mainIntent and authIntent before using them
        mainIntent = Intent(this, MainActivity::class.java)
        authIntent = Intent(this, AuthActivity::class.java)
        splashIntent = Intent(this, SplashActivity::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (prefs.autoLogin) {
                Log.d("autoLogin", "true")
                startActivity(mainIntent)
            } else {
                Log.d("autoLogin", "false")
                startActivity(authIntent)
            }
        } else {
            startActivity(splashIntent)
        }

    }

}