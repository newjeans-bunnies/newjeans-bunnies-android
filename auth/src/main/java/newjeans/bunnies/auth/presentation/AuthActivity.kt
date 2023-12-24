package newjeans.bunnies.auth.presentation

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import dagger.hilt.android.AndroidEntryPoint

import newjeans.bunnies.auth.presentation.navigation.NavigationRoute
import newjeans.bunnies.auth.viewmodel.LoginViewModel
import newjeans.bunnies.auth.viewmodel.SignupViewModel


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val signupViewModel: SignupViewModel by viewModels()

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
                        loginViewModel = loginViewModel,
                        onNavigateToSignup = { navController.navigate(NavigationRoute.signupRoute) }
                    )
                }
                composable(NavigationRoute.signupRoute) {
                    SignupScreen(
                        signupViewModel = signupViewModel,
                        onNavigateToLogin = { navController.navigate(NavigationRoute.loginRoute) }
                    )
                }
            }
        }
    }
}