package newjeans.bunnies.network.post.dto.response

data class PostGoodResponseDto(
    val postId: String,
    val goodCount: Long,
    val goodStatus: Boolean
)
