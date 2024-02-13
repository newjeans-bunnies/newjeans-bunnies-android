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
import newjeans.bunnies.auth.state.LoginState
import newjeans.bunnies.data.PreferenceManager

import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto

import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _hidePassword = MutableLiveData(false)
    val hidePassword: LiveData<Boolean>
        get() = _hidePassword


    private val _autoLoginStatus = MutableLiveData<Boolean>()
    val autoLoginStatus: LiveData<Boolean>
        get() = _autoLoginStatus

    private val _loginErrorStatus = MutableLiveData<Boolean>()

    val loginErrorStatus: LiveData<Boolean>
        get() = _loginErrorStatus

    private var _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState


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
                _loginErrorStatus.value = false
                prefs.accessToken = it.accessToken
                prefs.autoLogin = autoLogin
                if (autoLogin) {
                    prefs.refreshToken = it.refreshToken
                    prefs.expiredAt = it.expiredAt
                } else {
                    prefs.expiredAt = ""
                    prefs.refreshToken = ""
                }
                _loginState.emit(LoginState(true, ""))
            }.onFailure { e ->
                _loginErrorStatus.value = true
                _loginState.emit(LoginState(false, e.message.toString()))
            }
        }
    }

    fun autoLogin(status: Boolean) {
        _autoLoginStatus.value = status
    }

    fun hidePassword(status: Boolean) {
        _hidePassword.value = status
    }

}