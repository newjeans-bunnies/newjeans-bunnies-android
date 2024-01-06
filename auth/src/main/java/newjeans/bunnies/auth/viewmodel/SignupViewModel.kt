package newjeans.bunnies.auth.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto

import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    //이용약관 동의
    private val _useAgreementButton = MutableLiveData(false)
    val useAgreementStatus: LiveData<Boolean>
        get() = _useAgreementButton

    //개인정보 동의
    private val _informationConsentButton = MutableLiveData(false)
    val informationConsentStatus: LiveData<Boolean>
        get() = _informationConsentButton

    //유저 아이디 중복 체크
    private val _userCheckStatus = MutableLiveData<Boolean>()
    val userCheckStatus: LiveData<Boolean>
        get() = _userCheckStatus

    //비밀번호 숨기기
    private val _hidePassword = MutableLiveData(false)
    val hidePassword: LiveData<Boolean>
        get() = _hidePassword

    private val _hideCheckPassword = MutableLiveData(false)
    val hideCheckPassword: LiveData<Boolean>
        get() = _hideCheckPassword

    //비밀번호
    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

    //비밀번호 확인
    private val _checkPassword = MutableLiveData("")
    val checkPassword: LiveData<String>
        get() = _checkPassword

    //아이디
    private val _userId = MutableLiveData("")
    val userId: LiveData<String>
        get() = _userId


    fun userId(userId: String){
        _userId.value = userId
    }

    fun password(password: String){
        _password.value = password
    }

    fun checkPassword(checkPassword: String){
        _checkPassword.value = checkPassword
    }

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

    fun signup(signupReqeustDto: SignupReqeustDto){
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signup(signupReqeustDto)
            }.onSuccess {
                when(it.status){
                    201 -> {

                    }
                    else -> {

                    }
                }
            }.onFailure {

            }
        }
    }
}