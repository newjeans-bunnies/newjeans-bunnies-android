package newjeans.bunnies.network.user

import newjeans.bunnies.network.user.dto.UserBasicDto
import newjeans.bunnies.network.user.dto.UserDetailDto
import newjeans.bunnies.network.user.dto.UserDto

interface UserRepository {

    suspend fun getUserDetailInformation(authorization: String): UserDetailDto
    suspend fun getUserBasicInformation(userId: String): UserBasicDto
    suspend fun userUpdate(authorization: String, id: String, userDto: UserDto)
    suspend fun deleteUser(authorization: String, userId: String)
}