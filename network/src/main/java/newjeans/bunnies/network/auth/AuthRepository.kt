package newjeans.bunnies.network.auth

import newjeans.bunnies.network.auth.dto.reqeust.CertificationVerifyRequestDto
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupRequestDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.RefreshResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto

interface AuthRepository {
    suspend fun login(loginRequestDto: LoginReqeustDto): LoginResponseDto
    suspend fun signup(signupRequestDto: SignupRequestDto): SignupResponseDto
    suspend fun reissueToken(refreshToken: String, accessToken: String): RefreshResponseDto
    suspend fun verify(certificationVerifyRequestDto: CertificationVerifyRequestDto): StatusResponseDto
    suspend fun certification(phoneNumber: String): StatusResponseDto
}