package newjeans.bunnies.network.auth

import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.response.StatusResponseDto

interface AuthRepository {
    suspend fun login(loginRequestDto: LoginReqeustDto): LoginResponseDto
    suspend fun signup(signupRequestDto: SignupReqeustDto): SignupResponseDto
    suspend fun refresh(token: String)

    suspend fun checkUser(userId: String): StatusResponseDto
}