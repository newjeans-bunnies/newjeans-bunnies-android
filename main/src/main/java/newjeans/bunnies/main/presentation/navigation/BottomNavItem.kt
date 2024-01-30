package newjeans.bunnies.main.presentation.navigation

import newjeans.bunnies.auth.presentation.navigation.NavigationRoute.alarmRoute
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute.myInfoRoute
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute.postRoute
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute.settingRoute
import newjeans.bunnies.auth.presentation.navigation.NavigationRoute.videoRoute

sealed class BottomNavItem (
    val title: String, val icon: Int, val screenRoute: String
){
//    object Settings: BottomNavItem(settingRoute, )
//    object Post: BottomNavItem(postRoute, )
//    object MyInfo: BottomNavItem(myInfoRoute)
//    object Video: BottomNavItem(videoRoute)
//    object Alarm: BottomNavItem(alarmRoute)
}