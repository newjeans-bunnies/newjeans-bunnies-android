package newjeans.bunnies.data


import android.content.Context
import android.content.SharedPreferences


class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(NEWJEANS_BUNNIES, Context.MODE_PRIVATE)

    var autoLogin: Boolean
        get() = prefs.getBoolean(AUTO_LOGIN, false)
        set(value) = prefs.edit().putBoolean(AUTO_LOGIN, value).apply()

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, "").toString()
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var refreshToken: String
        get() = prefs.getString(REFRESH_TOKEN, "").toString()
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    fun deleteToken() {
        prefs.edit().remove(AUTO_LOGIN).apply()
        prefs.edit().remove(ACCESS_TOKEN).apply()
        prefs.edit().remove(REFRESH_TOKEN).apply()
    }

    companion object {
        const val NEWJEANS_BUNNIES = "NEWJEANS_BUNNIES"
        const val AUTO_LOGIN = "AUTO_LOGIN"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}