package newjeans.bunnies.main.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import newjeans.bunnies.main.presentation.post.data.PostData
import newjeans.bunnies.main.presentation.post.state.PostInfoState

import newjeans.bunnies.network.post.PostRepository
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto
import newjeans.bunnies.network.post.dto.response.PostBasicInfoResponseDto
import newjeans.bunnies.network.post.dto.response.PostImageResponseDto
import java.time.LocalDateTime

import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    companion object {
        const val TAG = "PostViewModel"
    }

    private val _lastDate = MutableLiveData<String>()
    val lastDate: LiveData<String>
        get() = _lastDate

    private val _postData = MutableLiveData<List<PostData>>(listOf())
    val postData: LiveData<List<PostData>>
        get() = _postData

    private val _postImage = MutableLiveData<List<PostImageResponseDto>>(listOf())
    val postImage: LiveData<List<PostImageResponseDto>>
        get() = _postImage

    private var _postInfoState = MutableSharedFlow<PostInfoState>()
    val postInfoState: SharedFlow<PostInfoState> = _postInfoState

    fun makePost(makePostRequestDto: MakePostRequestDto) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.makePost(makePostRequestDto)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun postGood(authorization: String, postId: String, userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postGood(authorization, postId, userId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun listPostBasicInfo() {
        Log.d(TAG, "listPostBasicInfo")
        val lastDate = if (_lastDate.value.isNullOrBlank()) {
            LocalDateTime.now().toString()
        } else {
            _lastDate.value?:LocalDateTime.now().toString()
        }
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.listPostBasicInfo(lastDate)
            }.onSuccess {
                if (it.isNotEmpty()) {
                    _lastDate.value = it[it.size - 1].createDate
                    if (_postData.value != null) {
                        _postData.value = _postData.value!! + it.map { postData ->
                            PostData(
                                uuid = postData.uuid,
                                userId = postData.userId,
                                createDate = postData.createDate,
                                goodStatus = null,
                                body = postData.body,
                                good = postData.good
                            )
                        }
                    }
                }
                _postInfoState.emit(PostInfoState(true, ""))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _postInfoState.emit(PostInfoState(false, e.message.toString()))
            }
        }
    }


    fun listPostDetail(authorization: String, userId: String) {
        val lastDate = if (_lastDate.value.isNullOrBlank()) {
            LocalDateTime.now().toString()
        } else {
            _lastDate.value?:LocalDateTime.now().toString()
        }
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.listPostDetail("Bearer $authorization", lastDate, userId)
            }.onSuccess {
                if (it.isNotEmpty()) {
                    _lastDate.value = it[it.size - 1].createDate
                    if (_postData.value != null) {
                        _postData.value = _postData.value!! + it.map { postData ->
                            PostData(
                                uuid = postData.uuid,
                                userId = postData.userId,
                                createDate = postData.createDate,
                                goodStatus = postData.goodStatus,
                                body = postData.body,
                                good = postData.good
                            )
                        }
                    }
                }
                _postInfoState.emit(PostInfoState(true, ""))
            }.onFailure { e ->
                _postInfoState.emit(PostInfoState(false, e.message.toString()))
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun userPostBasicInfo(userId: String, date: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.userPostBasicInfo(userId, date)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun postDetail(authorization: String, uuid: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postDetail(authorization, uuid)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deletePost(authorization: String, postId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.deletePost(authorization, postId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun getPostImage(postId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postImage(postId)
            }.onSuccess {
                _postImage.value = it
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

}