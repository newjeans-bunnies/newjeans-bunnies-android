package newjeans.bunnies.network.user

import newjeans.bunnies.network.auth.dto.response.CheckSupportResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto
import newjeans.bunnies.network.user.dto.response.UserImageResponseDto
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserDetails(
        authorization: String
    ): UserDetailDto {
        return userApi.getUserDetails(authorization)
    }

    override suspend fun getUserBasicInfo(userId: String): UserBasicDto {
        return userApi.getUserBasicInfo(userId)
    }

    override suspend fun updateUser(authorization: String, id: String, userDto: UserDto) {
        return userApi.updateUser(authorization, id, userDto)
    }

    override suspend fun deleteUser(authorization: String, userId: String): StatusResponseDto {
        return userApi.deleteUser(authorization, userId)
    }

    override suspend fun checkUserId(userId: String): StatusResponseDto {
        return userApi.checkUserId(userId)
    }

    override suspend fun checkPhoneNumber(phoneNumber: String): StatusResponseDto {
        return userApi.checkPhoneNumber(phoneNumber)
    }

    override suspend fun checkSupport(): CheckSupportResponseDto {
        return userApi.checkSupport()
    }

    override suspend fun getUserImage(userId: String): UserImageResponseDto {
        return userApi.getUserImage(userId)
    }

}