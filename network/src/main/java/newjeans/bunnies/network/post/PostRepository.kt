package newjeans.bunnies.network.post


import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto
import newjeans.bunnies.network.post.dto.response.MakePostResponseDto
import newjeans.bunnies.network.post.dto.response.PostBasicInfoResponseDto
import newjeans.bunnies.network.post.dto.response.PostDetailResponseDto
import newjeans.bunnies.network.post.dto.response.PostGoodResponseDto

interface PostRepository {

    // 게시글 만들기
    suspend fun makePost(makePostRequestDto: MakePostRequestDto): MakePostResponseDto


    // 게시글 좋아요
    suspend fun postGood(authorization: String, postId: String, userId: String): PostGoodResponseDto


    // 게시글 여러개 가져오기
    suspend fun listPostBasicInfo(date: String): List<PostBasicInfoResponseDto>
    suspend fun listPostDetail(authorization: String, date: String, userId: String): List<PostDetailResponseDto>


    // 특정 유저 게시글 가져오기
    suspend fun userPostBasicInfo(userId: String, date: String): List<PostBasicInfoResponseDto>
    suspend fun userPostDetail(userId: String, date: String): List<PostDetailResponseDto>


    //단일 게시글 가져오기
    suspend fun postBasicInfo(uuid: String): PostBasicInfoResponseDto
    suspend fun postDetail(authorization: String, userId: String): PostDetailResponseDto


    //게시글 삭제하기
    suspend fun deletePost(authorization: String, postId: String): StatusResponseDto
}