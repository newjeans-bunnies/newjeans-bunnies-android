package newjeans.bunnies.network.auth.dto.reqeust

data class SignupRequestDto(
    val userId: String,
    val password: String,
    val phoneNumber: String,
    val country: String,
    val language: String,
    val birth: String,
    val authority: String
)
