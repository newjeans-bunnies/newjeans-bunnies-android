package newjeans.bunnies.network.auth.dto.response

data class RefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val expirationTime: String,
    val authority: String
)
