package newjeans.bunnies.auth.viewmodel


import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import newjeans.bunnies.auth.state.ReissueTokenState
import newjeans.bunnies.auth.state.UserDetailInformationState

import newjeans.bunnies.auth.state.login.CheckSupportState
import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.user.UserRepository

import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    ) : ViewModel() {

    companion object {
        const val TAG = "AuthViewModel"
    }

    private val _checkSupportState = MutableSharedFlow<CheckSupportState>()
    val checkSupportState: SharedFlow<CheckSupportState> = _checkSupportState

    private var _countrys = MutableLiveData<List<String>>()
    val countrys: LiveData<List<String>> = _countrys

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> =
        _getUserDetailInformationState

    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState

    fun checkSupport() {
        viewModelScope.launch {
            val countrys = countrys.value
            if (countrys.isNullOrEmpty()) {
                kotlin.runCatching {
                    userRepository.checkSupport()
                }.onSuccess {
                    _countrys.value = it.country
                    _checkSupportState.emit(CheckSupportState(true))
                }.onFailure { e ->
                    _checkSupportState.emit(CheckSupportState(false, e.message.toString()))
                    Log.d(TAG, e.message.toString())
                }
            } else {
                _checkSupportState.emit(CheckSupportState(true))
            }
        }
    }

    fun getUserDetailInformation() {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserDetails()
            }.onSuccess {
                Log.d(TAG, it.toString())
                _getUserDetailInformationState.emit(UserDetailInformationState(true, ""))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
            }
        }
    }
}