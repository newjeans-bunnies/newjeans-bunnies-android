package newjeans.bunnies.main.presentation.post.data

data class PostData(
    val uuid: String,
    val userId: String,
    val createDate: String,
    val goodStatus: Boolean,
    val body: String,
    val good: Long
)
