package newjeans.bunnies.main.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import newjeans.bunnies.data.TokenManager
import newjeans.bunnies.main.data.PostData
import newjeans.bunnies.main.state.PostGoodState
import newjeans.bunnies.main.state.PostImageState
import newjeans.bunnies.main.state.PostInfoState
import newjeans.bunnies.main.state.ReissueTokenState
import newjeans.bunnies.network.auth.AuthRepository

import newjeans.bunnies.network.post.PostRepository
import newjeans.bunnies.network.post.dto.request.MakePostRequestDto
import newjeans.bunnies.network.post.dto.response.PostImageResponseDto
import java.time.LocalDateTime

import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
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

    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState

    private val _postImageState = MutableSharedFlow<PostImageState>()
    val postImageState: SharedFlow<PostImageState> = _postImageState

    private val _postGoodState = MutableSharedFlow<PostGoodState>()
    val postGoodState: SharedFlow<PostGoodState> = _postGoodState

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

    fun postGood(postId: String, userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postGood(postId, userId)
            }.onSuccess {
                _postGoodState.emit(
                    PostGoodState(
                        true,
                        "",
                        goodStatus = it.goodStatus,
                        goodCounts = it.goodCount
                    )
                )
            }.onFailure { e ->
                _postGoodState.emit(PostGoodState(false, e.message.toString()))
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun listPostBasicInfo() {
        val lastDate = if (_lastDate.value.isNullOrBlank()) {
            LocalDateTime.now().toString()
        } else {
            _lastDate.value ?: LocalDateTime.now().toString()
        }
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.listPostBasicInfo(lastDate)
            }.onSuccess {
                if (it.isNotEmpty()) {
                    _lastDate.value = it[it.size - 1].createDate
                    if (_postData.value != null) {
                        _postData.value = _postData.value!! + it.map {
                            PostData(
                                postId = it.uuid,
                                userId = it.userId,
                                postCreateDate = it.createDate,
                                goodStatus = null,
                                postBody = it.body,
                                goodCounts = it.good,
                                postImage = it.images,
                                userImage = it.userImage
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


    fun listPostDetail() {
        val lastDate = if (_lastDate.value.isNullOrBlank()) {
            LocalDateTime.now().toString()
        } else {
            _lastDate.value ?: LocalDateTime.now().toString()
        }
        viewModelScope.launch {
            val userId = tokenManager.getUserId().first()
            kotlin.runCatching {
                postRepository.listPostDetail(lastDate, userId)
            }.onSuccess {
                if (it.isNotEmpty()) {
                    _lastDate.value = it[it.size - 1].createDate
                    if (_postData.value != null) {
                        _postData.value = _postData.value!! + it.map {
                            PostData(
                                postId = it.uuid,
                                userId = it.userId,
                                postCreateDate = it.createDate,
                                goodStatus = null,
                                postBody = it.body,
                                goodCounts = it.good,
                                postImage = it.images,
                                userImage = it.userImage
                            )
                        }
                    }
                }
                _postInfoState.emit(PostInfoState(true, ""))
            }.onFailure { e ->
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

    fun postDetail(uuid: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.postDetail(uuid)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.deletePost(postId)
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
                _postImageState.emit(PostImageState(true, "", it))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _postImageState.emit(PostImageState(false, e.message.toString()))
            }
        }
    }
}