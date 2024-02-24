package newjeans.bunnies.auth.state.signup

data class PhoneNumberCheckState(
    val isSuccess: Boolean = false,
    val error: String = "",
    val value: String = ""
)
