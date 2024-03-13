package newjeans.bunnies.network.auth.dto.response

data class CheckSupportResponseDto(
    val country: List<String>,
    val fileFormat: List<String>
)