package newjeans.bunnies.auth.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import newjeans.bunnies.network.auth.AuthRepository

import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject
constructor() : ViewModel() {

    //비밀번호 숨기기
    private val _hidePassword = MutableLiveData(false)
    val hidePassword: LiveData<Boolean>
        get() = _hidePassword

    private val _hideCheckPassword = MutableLiveData(false)
    val hideCheckPassword: LiveData<Boolean>
        get() = _hideCheckPassword



    fun useAgreementButton(status: Boolean) {
        _useAgreementButton.value = status
    }

    fun informationConsentButton(status: Boolean) {
        _informationConsentButton.value = status
    }

    fun hidePassword(status: Boolean){
        _hidePassword.value = status
    }

    fun hideCheckPassword(status: Boolean){
        _hideCheckPassword.value = status
    }


    fun checkUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.checkUser(userId)
            }.onSuccess {
                when (it.status) {
                    200 -> _userCheckStatus.value = true
                    else -> _userCheckStatus.value = false
                }

            }.onFailure {
                _userCheckStatus.value = false
            }
        }
    }
}