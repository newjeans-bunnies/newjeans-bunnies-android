package newjeans.bunnies.main.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

import kotlinx.coroutines.launch
import newjeans.bunnies.main.state.UserDetailInformationState
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

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> =
        _getUserDetailInformationState


    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState

    fun getUserBasicInformation(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserBasicInfo(userId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun userUpdate(id: String, userDto: UserDto) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.updateUser(id, userDto)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.deleteUser(userId)
            }.onSuccess {

            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

}