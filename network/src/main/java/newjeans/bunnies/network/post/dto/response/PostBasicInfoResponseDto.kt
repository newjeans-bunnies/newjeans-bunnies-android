package newjeans.bunnies.network.post.dto.response

data class PostBasicInfoResponseDto(
    val uuid: String,
    val userId: String,
    val body: String,
    val createDate: String,
    val good: Long
)
