package newjeans.bunnies.auth


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp

import dagger.hilt.android.AndroidEntryPoint
import newjeans.bunnies.auth.presentation.LoginScreen
import newjeans.bunnies.auth.presentation.SignupScreen

import newjeans.bunnies.auth.presentation.navigation.NavigationRoute
import newjeans.bunnies.auth.viewmodel.LoginViewModel
import newjeans.bunnies.auth.viewmodel.SignupViewModel
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    companion object {
        lateinit var prefs: PreferenceManager
    }

    private val loginViewModel: LoginViewModel by viewModels()
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager(this)
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.loginRoute
            ) {
                composable(NavigationRoute.loginRoute) {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onNavigateToSignup = { navController.navigate(NavigationRoute.signupRoute) },
                        toMain = { nextActivity() },
                        prefs =  prefs
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen(
                        viewModel = signupViewModel,
                        onNavigateToLogin = { navController.navigate(NavigationRoute.loginRoute) },
                        this@AuthActivity
                    )
                }
            }
        }
    }

    private fun nextActivity(){
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }


    override fun attachBaseContext(newBase: Context) {
        val override = Configuration(newBase.resources.configuration)
        override.fontScale = 1.0f
        applyOverrideConfiguration(override)
        super.attachBaseContext(newBase)
    }

}