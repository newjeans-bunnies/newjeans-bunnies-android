package newjeans.bunnies.network.auth.dto.response

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String,
    val authority: String
)
