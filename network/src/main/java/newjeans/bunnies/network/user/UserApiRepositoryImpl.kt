package newjeans.bunnies.network.user

import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserDetailInformation(
        authorization: String
    ): UserDetailDto {
        return userApi.getUserDetailInformation(authorization)
    }

    override suspend fun getUserBasicInformation(userId: String): UserBasicDto {
        return userApi.getUserBasicInformation(userId)
    }

    override suspend fun userUpdate(authorization: String, id: String, userDto: UserDto) {
        return userApi.userUpdate(authorization, id, userDto)
    }

    override suspend fun deleteUser(authorization: String, userId: String) {
        return userApi.deleteUser(authorization, userId)
    }

}