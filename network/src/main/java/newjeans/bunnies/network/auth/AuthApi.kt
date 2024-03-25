package newjeans.bunnies.network.auth


import newjeans.bunnies.network.auth.dto.reqeust.CertificationVerifyRequestDto
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupRequestDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.RefreshResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApi {
    @POST("/api/auth/login")
    @Headers("Auth: false")
    suspend fun login(
        @Body loginRequestDto: LoginReqeustDto
    ): LoginResponseDto

    @POST("/api/auth/signup")
    @Headers("Auth: false")
    suspend fun signup(
        @Body signupRequestDto: SignupRequestDto
    ): SignupResponseDto

    @PATCH("/api/auth/refresh")
    suspend fun reissueToken(
        @Header("refresh-token") refreshToken: String,
        @Header("access-token") accessToken: String,
    ): Response<RefreshResponseDto>

    @POST("/api/auth/phonenumber/verify")
    @Headers("Auth: false")
    suspend fun verify(@Body certificationVerifyRequestDto: CertificationVerifyRequestDto): StatusResponseDto

    @POST("/api/auth/phonenumber")
    suspend fun certification(
        @Query("phonenumber") phoneNumber: String
    ): StatusResponseDto

}
