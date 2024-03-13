package newjeans.bunnies.auth.state.login

data class CheckSupportState(
    val isSuccess: Boolean = false,
    val error: String = ""
)