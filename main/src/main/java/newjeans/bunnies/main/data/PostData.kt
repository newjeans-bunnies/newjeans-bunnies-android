package newjeans.bunnies.main.data

data class PostData(
    val postId: String,
    val userId: String,
    val userImage: String,
    val postCreateDate: String,
    val postBody: String,
    val postImage: List<String>,
    val goodCounts: Long,
    val goodStatus: Boolean?,
)
