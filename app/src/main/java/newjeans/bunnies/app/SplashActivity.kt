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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import newjeans.bunnies.auth.AuthActivity
import newjeans.bunnies.auth.viewmodel.AuthViewModel
import newjeans.bunnies.main.MainActivity
import java.time.LocalDateTime
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = NewJeansBunniesApplication.prefs

        val authIntent = Intent(this, AuthActivity::class.java)
        val mainIntent = Intent(this, MainActivity::class.java)

        authIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (autoLogin(prefs)) {
                splashViewModel.reissueToken(prefs.refreshToken, prefs)
                lifecycleScope.launch {
                    splashViewModel.reissueTokenState.collect {
                        if (it.isSuccess) {
                            Log.d("자동로그인", "1authIntent")
                            startActivity(authIntent)
                            finish()
                        }
                        if (it.error.isNotEmpty()) {
                            Log.d("자동로그인", "mainIntent")
                            startActivity(mainIntent)
                            finish()
                        }
                    }
                }
            } else {
                Log.d("자동로그인", "2authIntent")
                startActivity(authIntent)
                finish()
            }
        } else {
            setContent {
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
                    if (autoLogin(prefs)) {
                        splashViewModel.reissueToken(prefs.refreshToken, prefs)
                        lifecycleScope.launch {
                            splashViewModel.reissueTokenState.collect {
                                if (it.isSuccess) {
                                    Log.d("자동로그인", "1authIntent")
                                    startActivity(authIntent)
                                    finish()
                                }
                                if (it.error.isNotEmpty()) {
                                    Log.d("자동로그인", "mainIntent")
                                    startActivity(mainIntent)
                                    finish()
                                }
                            }
                        }
                    } else {
                        Log.d("자동로그인", "2authIntent")
                        startActivity(authIntent)
                        finish()
                    }
                }, 1000)
            }
        }
    }
}
