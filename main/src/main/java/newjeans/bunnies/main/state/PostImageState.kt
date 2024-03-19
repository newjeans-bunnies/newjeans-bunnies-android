package newjeans.bunnies.main.state

import newjeans.bunnies.network.post.dto.response.PostImageResponseDto

data class PostImageState(
    val isSuccess: Boolean = false,
    val error: String = "",
    val values: List<PostImageResponseDto> = listOf()
)
