package newjeans.bunnies.network.user

import newjeans.bunnies.network.auth.dto.response.CheckSupportResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto
import newjeans.bunnies.network.user.dto.response.UserImageResponseDto

interface UserRepository {

    suspend fun getUserDetails(): UserDetailDto
    suspend fun getUserBasicInfo(userId: String): UserBasicDto
    suspend fun updateUser(id: String, userDto: UserDto)
    suspend fun deleteUser(userId: String): StatusResponseDto
    suspend fun checkUserId(userId: String): StatusResponseDto
    suspend fun checkPhoneNumber(phoneNumber: String): StatusResponseDto
    suspend fun checkSupport(): CheckSupportResponseDto
    suspend fun getUserImage(userId: String): UserImageResponseDto
}