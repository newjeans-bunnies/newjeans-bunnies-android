package newjeans.bunnies.auth.state.login

data class LoginState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
