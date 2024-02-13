package newjeans.bunnies.auth


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import newjeans.bunnies.auth.presentation.LoginScreen
import newjeans.bunnies.auth.presentation.SignupScreen

import newjeans.bunnies.auth.presentation.navigation.NavigationRoute
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.viewmodel.UserViewModel


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    companion object {
        lateinit var prefs: PreferenceManager
        const val TAG = "AuthActivity"
    }

    private val userViewModel: UserViewModel by viewModels()

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
                        onNavigateToSignup = { navController.navigate(NavigationRoute.signupRoute) },
                        toMain = { nextActivity() },
                        prefs = prefs
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen(
                        onNavigateToLogin = { navController.navigate(NavigationRoute.loginRoute) },
                        context = this@AuthActivity
                    )
                }
            }
        }
    }

    private fun nextActivity() {
        userViewModel.getUserDetailInformation(prefs.accessToken, prefs)
        lifecycleScope.launch {
            userViewModel.getUserDetailInformationState.collect {
                if (it.isSuccess) {
                    val mainIntent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(mainIntent)
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