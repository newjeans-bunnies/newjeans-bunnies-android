package newjeans.bunnies.network.auth


import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto

import javax.inject.Inject


class ApiRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(loginRequestDto: LoginReqeustDto): LoginResponseDto {
        return authApi.login(loginRequestDto)
    }

    override suspend fun signup(signupRequestDto: SignupReqeustDto): SignupResponseDto {
        return authApi.signup(signupRequestDto)
    }

    override suspend fun refresh(token: String) {
        return authApi.refresh(token)
    }

    override suspend fun checkUserId(userId: String): StatusResponseDto {
        return authApi.checkUserID(userId)
    }

    override suspend fun checkPhoneNumber(phoneNumber: String): StatusResponseDto {
        return authApi.checkPhoneNumber(phoneNumber)
    }


}