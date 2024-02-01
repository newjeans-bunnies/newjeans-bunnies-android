package newjeans.bunnies.main.presentation.navigation

import newjeans.bunnies.main.presentation.navigation.NavigationRoute.imageRoute
import newjeans.bunnies.main.presentation.navigation.NavigationRoute.myInfoRoute
import newjeans.bunnies.main.presentation.navigation.NavigationRoute.postRoute
import newjeans.bunnies.main.presentation.navigation.NavigationRoute.settingsRoute
import newjeans.bunnies.main.presentation.navigation.NavigationRoute.videoRoute
import newjeans.bunnies.designsystem.R

sealed class BottomNavItem (
    val title: String, val icon: Int, val screenRoute: String
){
    object Settings: BottomNavItem(settingsRoute, R.drawable.ic_settings, settingsRoute)
    object Post: BottomNavItem(postRoute, R.drawable.ic_post, postRoute)
    object MyInfo: BottomNavItem(myInfoRoute, R.drawable.ic_myinfo, myInfoRoute)
    object Video: BottomNavItem(videoRoute, R.drawable.ic_video, videoRoute)
    object Image: BottomNavItem(imageRoute, R.drawable.ic_image, imageRoute)
}