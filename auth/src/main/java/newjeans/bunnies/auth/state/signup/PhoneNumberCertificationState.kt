package newjeans.bunnies.auth.state.signup

data class PhoneNumberCertificationState(
    val isSuccess: Boolean = false,
    val error: String = "",
    val value: String = ""
)
