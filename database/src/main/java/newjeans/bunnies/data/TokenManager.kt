package newjeans.bunnies.data


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val TAG = "TokenManager"
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val LOGIN_CHECK = booleanPreferencesKey("login_check")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_IMAGE = stringPreferencesKey("user_image")
        private val USER_PHONENUMBER = stringPreferencesKey("user_phonenumber")
    }

    private val Context.accessTokenDataStore by preferencesDataStore("ACCESS_TOKEN_DATASTORE")
    private val Context.refreshTokenDataStore by preferencesDataStore("REFRESH_TOKEN_DATASTORE")
    private val Context.loginCheckDataStore by preferencesDataStore("LOGIN_CHECK_DATASTORE")
    private val Context.userDataStore by preferencesDataStore("USER_DATASTORE")

    suspend fun saveAccessToken(accessToken: String) {
        context.accessTokenDataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.refreshTokenDataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun saveAutoLoginStatus(autoLoginStatus: Boolean) {
        context.loginCheckDataStore.edit { prefs ->
            prefs[LOGIN_CHECK] = autoLoginStatus
        }
    }

    suspend fun saveUserData(userData: UserData) {
        context.userDataStore.edit { prefs ->
            prefs[USER_ID] = userData.userId
            prefs[USER_IMAGE] = userData.userImage
            prefs[USER_PHONENUMBER] = userData.userPhoneNumber
        }
    }

    fun getUserData(): Flow<List<String>> {
        return context.userDataStore.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { prefs ->
            prefs.asMap().values.toList().map {
                it.toString()
            }
        }
    }


    fun getAccessToken(): Flow<String> {
        return context.accessTokenDataStore.data.map { prefs ->
            prefs.asMap().values.toString()
        }
    }

    fun getRefreshToken(): Flow<String> {
        return context.refreshTokenDataStore.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { prefs ->
            prefs.asMap().values.toString()
        }
    }

    fun getAutologin(): Flow<Boolean> {
        return context.loginCheckDataStore.data.map { prefs ->
            prefs[LOGIN_CHECK] ?: false
        }
    }
}