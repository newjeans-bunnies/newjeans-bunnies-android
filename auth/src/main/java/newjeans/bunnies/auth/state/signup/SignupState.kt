package newjeans.bunnies.auth.state.signup

data class SignupState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
