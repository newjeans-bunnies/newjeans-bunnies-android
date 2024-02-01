package newjeans.bunnies.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint

import newjeans.bunnies.main.presentation.navigation.NavigationRoute
import newjeans.bunnies.main.presentation.ImageScreen
import newjeans.bunnies.main.presentation.MyInfoScreen
import newjeans.bunnies.main.presentation.post.PostScreen
import newjeans.bunnies.main.presentation.SettingsScreen
import newjeans.bunnies.main.presentation.VideoScreen
import newjeans.bunnies.main.presentation.navigation.BottomNavItem
import newjeans.bunnies.main.utils.NoRippleInteractionSource
import newjeans.bunnies.main.viewmodel.PostViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigation(navController = navController) }
            ) {
                Box(Modifier.padding(it)) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavigationRoute.postRoute) {
            composable(NavigationRoute.postRoute) {
                PostScreen(postViewModel)
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
            BottomNavItem.MyInfo,
        )

        androidx.compose.material.BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val noIndication = rememberRipple(bounded = false, color = Color.Transparent)

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
                        navController.navigate(item.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }
}