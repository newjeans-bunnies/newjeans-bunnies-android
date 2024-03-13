package newjeans.bunnies.auth


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import dagger.hilt.android.AndroidEntryPoint

import newjeans.bunnies.auth.presentation.LoginScreen
import newjeans.bunnies.auth.presentation.SignupScreen
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute
import newjeans.bunnies.auth.viewmodel.AuthViewModel
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    companion object {
        lateinit var prefs: PreferenceManager
        const val TAG = "AuthActivity"
    }

    private val signupViewModel: SignupViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var mainActivityIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager(this)
        mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = NavigationRoute.loginRoute
            ) {
                composable(NavigationRoute.loginRoute) {
                    LoginScreen(
                        onNavigateToSignup = { authViewModel.checkSupport() },
                        prefs = prefs,
                        toMain = { startActivity(mainActivityIntent) }
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen(
                        onNavigateToLogin = { navController.navigate(NavigationRoute.loginRoute) },
                        signupViewModel = signupViewModel,
                        authViewModel = authViewModel
                    )
                }
            }

            LaunchedEffect(authViewModel.checkSupportState) {
                authViewModel.checkSupportState.collect {
                    if (it.isSuccess) navController.navigate(NavigationRoute.signupRoute)
                }
            }

            LaunchedEffect(authViewModel.reissueTokenState) {
                authViewModel.reissueTokenState.collect {
                    prefs.deleteUserData()
                    prefs.deleteToken()
                }
            }
        }
    }


    override fun attachBaseContext(newBase: Context) {
        val override = Configuration(newBase.resources.configuration)
        override.fontScale = 1.0f
        applyOverrideConfiguration(override)
        super.attachBaseContext(newBase)
    }

}