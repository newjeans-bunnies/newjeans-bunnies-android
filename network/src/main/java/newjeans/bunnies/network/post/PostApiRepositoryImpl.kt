package newjeans.bunnies.network.post


import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto
import newjeans.bunnies.network.post.dto.response.MakePostResponseDto
import newjeans.bunnies.network.post.dto.response.PostBasicInfoResponseDto
import newjeans.bunnies.network.post.dto.response.PostDetailResponseDto
import newjeans.bunnies.network.post.dto.response.PostGoodResponseDto
import newjeans.bunnies.network.post.dto.response.PostImageResponseDto

import javax.inject.Inject


class PostApiRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) : PostRepository {
    override suspend fun makePost(makePostRequestDto: MakePostRequestDto): MakePostResponseDto {
        return postApi.makePost(makePostRequestDto)
    }

    override suspend fun postImage(postId: String): List<PostImageResponseDto> {
        return postApi.postImage(postId)
    }

    override suspend fun postGood(
        authorization: String,
        postId: String,
        userId: String
    ): PostGoodResponseDto {
        return postApi.postGood(authorization, postId, userId)
    }

    override suspend fun listPostBasicInfo(date: String): List<PostBasicInfoResponseDto> {
        return postApi.listPostBasicInfo(date)
    }

    override suspend fun listPostDetail(
        authorization: String,
        date: String,
        userId: String
    ): List<PostDetailResponseDto> {
        return postApi.listPostDetail(authorization, date, userId)
    }

    override suspend fun userPostBasicInfo(
        userId: String,
        date: String
    ): List<PostBasicInfoResponseDto> {
        return postApi.userPostBasicInfo(userId, date)
    }

    override suspend fun userPostDetail(userId: String, date: String): List<PostDetailResponseDto> {
        return postApi.userPostDetail(userId, date)
    }

    override suspend fun postBasicInfo(uuid: String): PostBasicInfoResponseDto {
        return postApi.postBasicInfo(uuid)
    }

    override suspend fun postDetail(authorization: String, userId: String): PostDetailResponseDto {
        return postApi.postDetail(authorization, userId)
    }

    override suspend fun deletePost(authorization: String, postId: String): StatusResponseDto {
        return postApi.deletePost(authorization, postId)
    }
}
