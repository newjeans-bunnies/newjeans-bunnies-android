package newjeans.bunnies.app.viewmodel

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

import newjeans.bunnies.auth.state.ReissueTokenState
import newjeans.bunnies.auth.state.UserDetailInformationState
import newjeans.bunnies.data.TokenManager
import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.user.UserRepository

import javax.inject.Inject


private const val TAG = "SplashViewModel"

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> =
        _getUserDetailInformationState

    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState


    fun getInfo() {
        getUserDetailInformation()
    }

    private fun getUserDetailInformation() {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserDetails()
            }.onSuccess {
                tokenManager.saveUserId(it.id)
                tokenManager.saveUserImage(it.imageUrl)
                tokenManager.saveUserPhoneNumber(it.phoneNumber)
                _getUserDetailInformationState.emit(UserDetailInformationState(true, ""))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }

}