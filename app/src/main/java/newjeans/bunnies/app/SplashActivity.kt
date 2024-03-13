package newjeans.bunnies.app


import android.content.Intent
import android.os.Bundle

import androidx.activity.viewModels
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import newjeans.bunnies.app.NewJeansBunniesApplication.Companion.prefs
import newjeans.bunnies.app.viewmodel.SplashViewModel
import newjeans.bunnies.auth.AuthActivity
import newjeans.bunnies.main.MainActivity

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var mainActivityIntent: Intent
    private lateinit var authActivityIntent: Intent

    private lateinit var splashScreenState: MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        mainActivityIntent = Intent(this, MainActivity::class.java)
        authActivityIntent = Intent(this, AuthActivity::class.java)

        authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        setContent {
            splashScreenState = remember { mutableStateOf(true) }
            splashScreen.setKeepOnScreenCondition { splashScreenState.value }
            if (prefs.autoLogin) {
                viewModel.getUserDetailInformation(
                    prefs = prefs, authorization = prefs.accessToken
                )
            } else {
                authActivity()
            }


            LaunchedEffect(viewModel.reissueTokenState) {
                viewModel.reissueTokenState.collect {
                    if (it.error.isNotEmpty()) authActivity()
                    if (it.isSuccess) {
                        viewModel.getUserDetailInformation(
                            prefs = prefs, authorization = prefs.accessToken
                        )
                    }
                }
            }

            LaunchedEffect(viewModel.getUserDetailInformationState) {
                viewModel.getUserDetailInformationState.collect {
                    if (it.isSuccess) mainActivity()
                    if (it.error.isNotEmpty()) authActivity()
                }
            }
        }
    }

    private fun mainActivity() {
        splashScreenState.value = false
        startActivity(mainActivityIntent)
        finish()
    }

    private fun authActivity() {
        splashScreenState.value = false
        startActivity(authActivityIntent)
        finish()
    }
}