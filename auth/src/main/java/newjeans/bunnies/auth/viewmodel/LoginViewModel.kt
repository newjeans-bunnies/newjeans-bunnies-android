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
import newjeans.bunnies.auth.state.login.LoginState
import newjeans.bunnies.data.PreferenceManager

import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.LoginReqeustDto

import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _autoLoginStatus = MutableLiveData<Boolean>()
    val autoLoginStatus: LiveData<Boolean>
        get() = _autoLoginStatus

    fun autoLoginStatus(status: Boolean) {
        _autoLoginStatus.value = status
    }


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
                with(prefs){
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

}