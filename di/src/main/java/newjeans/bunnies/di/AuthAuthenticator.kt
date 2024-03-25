package newjeans.bunnies.di

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import newjeans.bunnies.data.TokenManager
import newjeans.bunnies.di.Constant.BASE_URL
import newjeans.bunnies.network.auth.AuthApi
import newjeans.bunnies.network.auth.dto.response.RefreshResponseDto
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    companion object {
        private const val TAG = "AuthAuthenticator"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { tokenManager.getRefreshToken().first() }
        val accessToken = runBlocking { tokenManager.getAccessToken().first() }
        return runBlocking {
            val reissueToken = reissueToken(refreshToken, accessToken)

            if (!reissueToken.isSuccessful || reissueToken.body() == null) {
                tokenManager.deleteData()
            }

            reissueToken.body()?.let {
                tokenManager.saveAccessToken(it.accessToken)
                tokenManager.saveRefreshToken(it.refreshToken)

                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }


    private suspend fun reissueToken(
        refreshToken: String,
        accessToken: String
    ): retrofit2.Response<RefreshResponseDto> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)
        return service.reissueToken(refreshToken, accessToken)
    }

}