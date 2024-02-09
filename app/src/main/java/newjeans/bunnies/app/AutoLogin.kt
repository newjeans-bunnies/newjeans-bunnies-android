package newjeans.bunnies.app

import newjeans.bunnies.data.PreferenceManager
import java.time.LocalDateTime

fun autoLogin(prefs: PreferenceManager): Boolean {
    val currentDateTime = LocalDateTime.now()
    val targetDateTime = LocalDateTime.parse(prefs.expiredAt)
    val comparisonResult = currentDateTime.compareTo(targetDateTime)

    return prefs.autoLogin && comparisonResult < 0
}