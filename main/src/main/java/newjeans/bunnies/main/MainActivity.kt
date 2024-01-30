package newjeans.bunnies.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.postRoute
            ){
//                composable(NavigationRoute.videoRoute){
//
//                }
//                composable(NavigationRoute.postRoute){
//
//                }
//                composable(NavigationRoute.myInfoRoute){
//
//                }
//                composable(NavigationRoute.settingRoute){
//
//                }
//                composable(NavigationRoute.alarmRoute){
//
//                }
            }
        }
    }
}