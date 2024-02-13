package newjeans.bunnies.app


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

import newjeans.bunnies.auth.AuthActivity
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.viewmodel.UserViewModel


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    companion object {
        lateinit var prefs: PreferenceManager
        const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager(this)

        val authIntent = Intent(this, AuthActivity::class.java)
        val mainIntent = Intent(this, MainActivity::class.java)

        authIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (prefs.autoLogin) {
                splashViewModel.reissueToken(prefs.refreshToken, prefs)
                lifecycleScope.launch {
                    splashViewModel.reissueTokenState.collect {
                        if (it.isSuccess) {
                            login(mainIntent, authIntent)
                        }
                        if (it.error.isNotEmpty()) {
                            Log.d("자동 로그인", it.toString())
                            logout(authIntent)
                        }
                    }
                }
            } else {
                logout(authIntent)
            }
        } else {
            setContent {
                SplashScreen(authIntent, mainIntent, prefs)
            }
        }
    }

    @Composable
    fun SplashScreen(authIntent: Intent, mainIntent: Intent, prefs: PreferenceManager) {
        Box(
            Modifier.fillMaxSize(), Alignment.Center
        ) {
            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(id = newjeans.bunnies.designsystem.R.drawable.ic_main),
                contentDescription = ""
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefs.autoLogin) {
                splashViewModel.reissueToken(prefs.refreshToken, prefs)
                lifecycleScope.launch {
                    splashViewModel.reissueTokenState.collect {
                        if (it.isSuccess) {
                            login(mainIntent, authIntent)
                        }
                        if (it.error.isNotEmpty()) {
                            logout(authIntent)
                        }
                    }
                }
            } else {
                logout(authIntent)
            }
        }, 1000)
    }


    private fun login(mainIntent: Intent, authIntent: Intent) {
        userViewModel.getUserDetailInformation(prefs.accessToken, prefs)
        lifecycleScope.launch {
            userViewModel.getUserDetailInformationState.collect {
                if (it.isSuccess) {
                    startActivity(mainIntent)
                    finish()
                }
                if (it.error.isNotEmpty()) {
                    Log.d(TAG, it.error)
                    logout(authIntent)
                }
            }
        }
    }

    private fun logout(authIntent: Intent) {
        prefs.deleteToken()
        prefs.deleteUserData()
        startActivity(authIntent)
        finish()
    }
}


