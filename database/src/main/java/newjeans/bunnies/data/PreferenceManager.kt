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

    var expiredAt: String
        get() = prefs.getString(EXPIRED_AT, "").toString()
        set(value) = prefs.edit().putString(EXPIRED_AT, value).apply()

    var userId: String
        get() = prefs.getString(USER_ID, "").toString()
        set(value) = prefs.edit().putString(USER_ID, value).apply()
    var userImage: String?
        get() = prefs.getString(USER_IMAGE, "").toString()
        set(value) = prefs.edit().putString(USER_IMAGE, value).apply()
    var userPhoneNumber: String
        get() = prefs.getString(USER_PHONENUMBER, "").toString()
        set(value) = prefs.edit().putString(USER_PHONENUMBER, value).apply()


    fun deleteToken() {
        prefs.edit().remove(AUTO_LOGIN).apply()
        prefs.edit().remove(ACCESS_TOKEN).apply()
        prefs.edit().remove(REFRESH_TOKEN).apply()
        prefs.edit().remove(EXPIRED_AT).apply()
    }

    fun deleteUserData() {
        prefs.edit().remove(USER_ID).apply()
        prefs.edit().remove(USER_PHONENUMBER).apply()
        prefs.edit().remove(USER_IMAGE).apply()
    }

    companion object {
        const val NEWJEANS_BUNNIES = "NEWJEANS_BUNNIES"
        const val AUTO_LOGIN = "AUTO_LOGIN"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val EXPIRED_AT = "EXPIRED_AT"
        const val USER_ID = "USER_ID"
        const val USER_IMAGE = "USER_IMAGE"
        const val USER_PHONENUMBER = "USER_PHONENUMBER"
    }
}