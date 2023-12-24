package newjeans.bunnies.auth.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

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


    private val _autoLogin = MutableLiveData(false)
    val autoLogin: LiveData<Boolean>
        get() = _autoLogin

    private val _loginError = MutableLiveData(false)

    val loginError: LiveData<Boolean>
        get() = _loginError


    fun login(userId: String, password: String, autoLogin: Boolean) {
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
//                val prefs = NewJeansBunniesApplication.prefs
//                prefs.accessToken = it.accessToken
//                prefs.autoLogin = autoLogin.value?:false
//                if(autoLogin.value == true)
//                    prefs.refreshToken = it.refreshToken
//                _loginError.value = false
            }.onFailure { e ->
                _loginError.value = true
                Log.d("LoginViewModel", e.message.toString())
            }
        }
    }

    fun loginError(status: Boolean) {
        _loginError.value = status
    }

    fun autoLogin(status: Boolean) {
        _autoLogin.value = status
    }

    fun hidePassword(status: Boolean) {
        _hidePassword.value = status
    }

}