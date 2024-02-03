package newjeans.bunnies.main.presentation.post.data

data class CommentData(
    val uuid: String,
    val userId: String,
    val body: String,
    val good: Long,
    val goodStatus: Boolean,
    val createDate: String
)