package newjeans.bunnies.auth.presentation


import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import newjeans.bunnies.auth.navigation.NavigationRoute


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.loginRoute
            ) {
                composable(NavigationRoute.loginRoute) {
                    LoginScreen(
                        onNavigateToSignup = { navController.navigate(NavigationRoute.signupRoute) }
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen()
                }
            }
        }
    }
}

