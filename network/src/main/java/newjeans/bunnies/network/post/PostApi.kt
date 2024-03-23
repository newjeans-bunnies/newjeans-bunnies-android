package newjeans.bunnies.network.post


import newjeans.bunnies.network.global.dto.response.StatusResponseDto
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto
import newjeans.bunnies.network.post.dto.response.MakePostResponseDto
import newjeans.bunnies.network.post.dto.response.PostBasicInfoResponseDto
import newjeans.bunnies.network.post.dto.response.PostDetailResponseDto
import newjeans.bunnies.network.post.dto.response.PostGoodResponseDto
import newjeans.bunnies.network.post.dto.response.PostImageResponseDto

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface PostApi {

    //게시글 만들기
    @POST("/api/post")
    suspend fun makePost(
        @Body makePostRequestDto: MakePostRequestDto
    ): MakePostResponseDto

    //게시글 좋아요
    @POST("/api/post/good")
    suspend fun postGood(
        @Query("post-id") postId: String,
        @Query("user-id") userId: String
    ): PostGoodResponseDto

    //게시글 사진 가져오기
    @GET("/api/post/image")
    suspend fun postImage(
        @Query("post-id") postId: String
    ): List<PostImageResponseDto>

    // 게시글 여러개 가져오기
    @GET("/api/post/basic-info")
    @Headers("Auth: false")
    suspend fun listPostBasicInfo(
        @Query("date") date: String
    ): List<PostBasicInfoResponseDto>

    @GET("/api/post/detail")
    suspend fun listPostDetail(
        @Query("date") date: String,
        @Query("userId") userId: String
    ): List<PostDetailResponseDto>

    // 특정 유저 게시글 가져오기
    @GET("api/post/user/basic-info/{userId}")
    @Headers("Auth: false")
    suspend fun userPostBasicInfo(
        @Path("userId") userId: String,
        @Query("date") date: String
    ): List<PostBasicInfoResponseDto>

    @GET("api/post/user/detail/{userId}")
    suspend fun userPostDetail(
        @Path("userId") userId: String,
        @Query("date") date: String
    ): List<PostDetailResponseDto>

    //단일 게시글 가져오기
    @GET("/api/post/basic-info/{uuid}")
    @Headers("Auth: false")
    suspend fun postBasicInfo(
        @Path(value = "uuid") uuid: String
    ): PostBasicInfoResponseDto

    @GET("/api/post/detail/{uuid}")
    suspend fun postDetail(
        @Path(value = "uuid") uuid: String
    ): PostDetailResponseDto

    //게시글 삭제하기
    @DELETE("/api/post/delete")
    suspend fun deletePost(
        @Query("postId") postId: String
    ): StatusResponseDto

}