package newjeans.bunnies.network.post.dto.response

data class PostGoodResponseDto(
    val postId: String,
    val good: Long,
    val goodStatus: Boolean
)
