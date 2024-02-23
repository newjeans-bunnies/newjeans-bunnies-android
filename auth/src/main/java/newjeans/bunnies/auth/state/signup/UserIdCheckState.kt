package newjeans.bunnies.auth.state.signup

data class UserIdCheckState(
    val isSuccess: Boolean = false,
    val error: String = "",
    val userId: String = ""
)
