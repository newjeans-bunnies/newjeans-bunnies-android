package newjeans.bunnies.network.auth


import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.RefreshResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequestDto: LoginReqeustDto
    ): LoginResponseDto

    @POST("/api/auth/signup")
    suspend fun signup(
        @Body signupRequestDto: SignupReqeustDto
    ): SignupResponseDto

    @PATCH("/api/auth/refresh")
    suspend fun refresh(
        @Header("refresh-token") token: String
    ): RefreshResponseDto

    @GET("/api/user/check/userid")
    suspend fun checkUserID(
        @Query("userId") userId: String
    ): StatusResponseDto


    @GET("/api/user/check/phonenumber")
    suspend fun checkPhoneNumber(
        @Query("phonenumber") phonenumber: String
    ): StatusResponseDto
}
