package newjeans.bunnies.main


import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState

import newjeans.bunnies.main.presentation.navigation.NavigationRoute
import newjeans.bunnies.main.presentation.ImageScreen
import newjeans.bunnies.main.presentation.MyInfoScreen
import newjeans.bunnies.main.presentation.post.PostScreen
import newjeans.bunnies.main.presentation.SettingsScreen
import newjeans.bunnies.main.presentation.VideoScreen
import newjeans.bunnies.main.presentation.navigation.BottomNavItem
import newjeans.bunnies.main.utils.NoRippleInteractionSource

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.viewmodel.UserViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
        lateinit var prefs: PreferenceManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager(this)

        Log.d("저장", "accessToken: " + prefs.accessToken)
        Log.d("저장", "refreshToken: " + prefs.refreshToken)
        Log.d("저장", "expirationTime: " + prefs.expiredAt)
        Log.d("토큰", prefs.accessToken)

        setContent {
            val navController = rememberNavController()
            Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
                Box(Modifier.padding(it)) {
                    NavigationGraph(navController)
                }
            }
        }


    }

    override fun onBackPressed() {

    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavigationRoute.postRoute) {
            composable(NavigationRoute.postRoute) {
                PostScreen(content = this@MainActivity)
            }
            composable(NavigationRoute.settingsRoute) {
                SettingsScreen()
            }
            composable(NavigationRoute.imageRoute) {
                ImageScreen()
            }
            composable(NavigationRoute.videoRoute) {
                VideoScreen()
            }
            composable(NavigationRoute.myInfoRoute) {
                MyInfoScreen()
            }
        }
    }

    @Composable
    fun BottomNavigation(navController: NavHostController) {
        val items = listOf(
            BottomNavItem.Post,
            BottomNavItem.Settings,
            BottomNavItem.Image,
            BottomNavItem.Video,
            BottomNavItem.MyInfo
        )

        androidx.compose.material.BottomNavigation(
            backgroundColor = Color.White, contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray,
                    selected = currentRoute == item.screenRoute,
                    alwaysShowLabel = false,
                    onClick = {
                        if (currentRoute != item.screenRoute) {
                            navController.navigate(item.screenRoute) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }
}