package newjeans.bunnies.auth.state

data class ReissueTokenState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
