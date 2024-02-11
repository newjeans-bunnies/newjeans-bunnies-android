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


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceManager(this)

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
                            Log.d("자동 로그인", it.toString())
                            startActivity(mainIntent)
                            finish()
                        }
                        if (it.error.isNotEmpty()) {
                            Log.d("자동 로그인", it.toString())
                            prefs.deleteToken()
                            startActivity(authIntent)
                            finish()
                        }
                    }
                }
            } else {
                prefs.deleteToken()
                startActivity(authIntent)
                finish()
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
                            Log.d("자동 로그인", it.toString())
                            startActivity(authIntent)
                            finish()
                        }
                        if (it.error.isNotEmpty()) {
                            Log.d("자동 로그인", it.toString())
                            startActivity(mainIntent)
                            finish()
                        }
                    }
                }
            } else {
                startActivity(authIntent)
                finish()
            }
        }, 1000)
    }
}


