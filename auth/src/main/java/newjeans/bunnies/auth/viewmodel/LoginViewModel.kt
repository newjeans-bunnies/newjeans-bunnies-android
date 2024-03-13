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
import newjeans.bunnies.auth.state.UserDetailInformationState
import newjeans.bunnies.auth.state.login.LoginState
import newjeans.bunnies.data.PreferenceManager

import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto
import newjeans.bunnies.network.user.UserRepository

import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    private val _autoLoginStatus = MutableLiveData<Boolean>()
    val autoLoginStatus: LiveData<Boolean>
        get() = _autoLoginStatus

    fun autoLoginStatus(status: Boolean) {
        _autoLoginStatus.value = status
    }

    private val _userId = MutableLiveData("")
    val userId: LiveData<String>
        get() = _userId

    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

    fun password(password: String) {
        _password.value = password
    }

    fun userId(id: String) {
        _userId.value = id
    }


    private var _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> = _getUserDetailInformationState


    fun login(autoLogin: Boolean, userId: String, password: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.login(
                    LoginReqeustDto(
                        userId = userId,
                        password = password
                    )
                )
            }.onSuccess {
                Log.d("login Success", it.toString())
                with(prefs) {
                    accessToken = it.accessToken
                    refreshToken = it.refreshToken
                    expiredAt = it.expiredAt
                    this.autoLogin = autoLogin
                }
                _loginState.emit(LoginState(true, ""))
            }.onFailure { e ->
                _loginState.emit(LoginState(false, e.message.toString()))
            }
        }
    }

    fun getUserDetailInformation(authorization: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserDetails("Bearer $authorization")
            }.onSuccess {
                Log.d(AuthViewModel.TAG, it.toString())
                prefs.userId = it.id
                prefs.userPhoneNumber = it.phoneNumber
                prefs.userImage = it.imageUrl
                _getUserDetailInformationState.emit(UserDetailInformationState(true))
            }.onFailure { e ->
                Log.d(AuthViewModel.TAG, e.message.toString())
                _getUserDetailInformationState.emit(UserDetailInformationState(false, e.message.toString()))
            }
        }
    }

}