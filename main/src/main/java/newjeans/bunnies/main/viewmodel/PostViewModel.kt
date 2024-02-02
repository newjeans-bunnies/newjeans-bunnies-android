package newjeans.bunnies.main.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import newjeans.bunnies.network.post.PostRepository
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto

import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    fun makePost(makePostRequestDto: MakePostRequestDto) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.makePost(makePostRequestDto)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun postGood(authorization: String, postId: String, userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postGood(authorization, postId, userId)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun listPostBasicInfo(date: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.listPostBasicInfo(date)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun listPostDetail(authorization: String, date: String, userId: String){
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.listPostDetail(authorization, date, userId)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun userPostBasicInfo(userId: String, date: String){
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.userPostBasicInfo(userId, date)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun postDetail(authorization: String, uuid: String){
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postDetail(authorization, uuid)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

    fun deletePost(authorization: String, postId: String){
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.deletePost(authorization, postId)
            }.onSuccess {

            }.onFailure {

            }
        }
    }

}