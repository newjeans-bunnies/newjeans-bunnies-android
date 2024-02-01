package newjeans.bunnies.network.post.dto.response

data class PostDetailResponseDto(
    val uuid: String,
    val userId: String,
    val body: String,
    val good: Long,
    val goodStatus: Boolean,
    val createDate: String
)
