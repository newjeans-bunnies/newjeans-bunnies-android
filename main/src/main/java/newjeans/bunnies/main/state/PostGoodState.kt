package newjeans.bunnies.main.state

data class PostGoodState(
    val isSuccess: Boolean = false,
    val error: String = "",
    val goodStatus: Boolean? = null,
    val goodCounts: Long = 0
)
