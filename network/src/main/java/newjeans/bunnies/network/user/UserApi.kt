package newjeans.bunnies.network.user


import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query


interface UserApi {
    @GET("/api/user/get-detail")
    suspend fun getUserDetailInformation(
        @Header("Authorization") authorization: String
    ): UserDetailDto


    @GET("/api/user/get-basic/{userId}")
    suspend fun getUserBasicInformation(
        @Path(value = "userId") userId: String
    ): UserBasicDto

    @PATCH("/api/user/update")
    suspend fun userUpdate(
        @Header("Authorization") authorization: String,
        @Query("id") id: String,
        @Body userDto: UserDto
    )

    @DELETE("/api/user/delete")
    suspend fun deleteUser(
        @Header("Authorization") authorization: String,
        @Query("userId") userId: String
    )
}