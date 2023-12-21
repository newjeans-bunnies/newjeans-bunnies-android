package newjeans.bunnies.network.auth

import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequestDto: LoginReqeustDto
    ): LoginResponseDto

    @POST("/api/auth/signup")
    suspend fun signup(
        @Body signupRequestDto: SignupReqeustDto
    ): SignupResponseDto

    @PATCH("/api/auth")
    suspend fun refresh(
        @Header("refresh-token") token: String
    )
}
