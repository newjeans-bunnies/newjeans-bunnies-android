package newjeans.bunnies.auth.state.signup

data class CertificationNumberVerifyState(
    val isSuccess: Boolean = false,
    val error: String = "",
)
