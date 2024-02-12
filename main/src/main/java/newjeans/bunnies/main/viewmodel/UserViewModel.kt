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
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.main.MainActivity
import newjeans.bunnies.main.data.UserData
import newjeans.bunnies.main.presentation.post.state.UserDetailInformationState
import newjeans.bunnies.main.state.ReissueTokenState
import newjeans.bunnies.network.auth.AuthRepository

import newjeans.bunnies.network.user.UserRepository
import newjeans.bunnies.network.user.dto.UserDto

import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    companion object {
        const val TAG = "UserViewModel"
    }

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> =
        _getUserDetailInformationState


    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState

    fun getUserDetailInformation(authorization: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserDetailInformation("Bearer $authorization")
            }.onSuccess {
                Log.d(TAG, it.toString())
                _userData.value?.userId = it.id
                _userData.value?.userImage = it.imageUrl
                _userData.value?.userPhoneNumber = it.phoneNumber
                _getUserDetailInformationState.emit(UserDetailInformationState(true, ""))
            }.onFailure { e ->
                _getUserDetailInformationState.emit(
                    UserDetailInformationState(
                        false,
                        e.message.toString()
                    )
                )
                reissueToken(prefs.refreshToken, prefs)
            }
        }
    }

    fun getUserBasicInformation(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserBasicInformation(userId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun userUpdate(authorization: String, id: String, userDto: UserDto) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.userUpdate("Bearer $authorization", id, userDto)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deleteUser(authorization: String, userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.deleteUser("Bearer $authorization", userId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun reissueToken(refreshToken: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.refresh(refreshToken)
            }.onSuccess {
                _reissueTokenState.emit(ReissueTokenState(true, ""))
                prefs.accessToken = it.accessToken
                prefs.expiredAt = it.expiredAt
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _reissueTokenState.emit(ReissueTokenState(false, e.message.toString()))
            }
        }
    }
}