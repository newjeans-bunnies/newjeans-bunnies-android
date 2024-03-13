package newjeans.bunnies.network.user


import newjeans.bunnies.network.auth.dto.response.CheckSupportResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto
import newjeans.bunnies.network.user.dto.response.UserImageResponseDto

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query


interface UserApi {
    @GET("/api/user/get-detail")
    suspend fun getUserDetails(
        @Header("Authorization") authorization: String
    ): UserDetailDto


    @GET("/api/user/get-basic/{userId}")
    suspend fun getUserBasicInfo(
        @Path(value = "userId") userId: String
    ): UserBasicDto

    @PATCH("/api/user/update")
    suspend fun updateUser(
        @Header("Authorization") authorization: String,
        @Query("id") id: String,
        @Body userDto: UserDto
    )

    @GET("/api/user/check/userid")
    suspend fun checkUserId(
        @Query("userId") userId: String
    ): StatusResponseDto


    @GET("/api/user/check/phonenumber")
    suspend fun checkPhoneNumber(
        @Query("phonenumber") phoneNumber: String
    ): StatusResponseDto

    @GET("/api/user/support")
    suspend fun checkSupport(): CheckSupportResponseDto

    @GET("/api/user/image/{userId}")
    suspend fun getUserImage(
        @Path("userId") userId: String
    ): UserImageResponseDto

    @DELETE("/api/user/delete")
    suspend fun deleteUser(
        @Header("Authorization") authorization: String,
        @Query("userId") userId: String
    ): StatusResponseDto
}