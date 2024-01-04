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


    private val _autoLoginStatus = MutableLiveData(false)
    val autoLoginStatus: LiveData<Boolean>
        get() = _autoLoginStatus

    private val _loginErrorStatus = MutableLiveData(false)

    val loginErrorStatus: LiveData<Boolean>
        get() = _loginErrorStatus

    private val _userId = MutableLiveData("")
    val userId: LiveData<String>
        get() = _userId

    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password


    fun login(autoLogin: Boolean) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.login(
                    LoginReqeustDto(
                        userId = userId.value?:"",
                        password = password.value?:""
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
                _loginErrorStatus.value = true
            }
        }
    }

    fun loginError(status: Boolean) {
        _loginErrorStatus.value = status
    }

    fun autoLogin(status: Boolean) {
        _autoLoginStatus.value = status
    }

    fun hidePassword(status: Boolean) {
        _hidePassword.value = status
    }

}