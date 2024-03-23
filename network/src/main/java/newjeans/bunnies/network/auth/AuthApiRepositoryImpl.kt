package newjeans.bunnies.network.auth


import newjeans.bunnies.network.auth.dto.reqeust.CertificationVerifyRequestDto
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupRequestDto
import newjeans.bunnies.network.auth.dto.response.LoginResponseDto
import newjeans.bunnies.network.auth.dto.response.RefreshResponseDto
import newjeans.bunnies.network.auth.dto.response.SignupResponseDto
import newjeans.bunnies.network.global.dto.response.StatusResponseDto

import javax.inject.Inject


class AuthApiRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(loginRequestDto: LoginReqeustDto): LoginResponseDto {
        return authApi.login(loginRequestDto)
    }

    override suspend fun signup(signupRequestDto: SignupRequestDto): SignupResponseDto {
        return authApi.signup(signupRequestDto)
    }

    override suspend fun reissueToken(
        refreshToken: String,
        accessToken: String
    ): RefreshResponseDto {
        return authApi.refresh(refreshToken, accessToken)
    }

    override suspend fun verify(certificationVerifyRequestDto: CertificationVerifyRequestDto): StatusResponseDto {
        return authApi.verify(certificationVerifyRequestDto)
    }

    override suspend fun certification(phoneNumber: String): StatusResponseDto {
        return authApi.certification(phoneNumber)
    }

}