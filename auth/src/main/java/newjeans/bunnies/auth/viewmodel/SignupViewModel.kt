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
    private val _useAgreementButton = MutableLiveData<Boolean>()
    val useAgreementStatus: LiveData<Boolean>
        get() = _useAgreementButton

    //개인정보 동의
    private val _informationConsentButton = MutableLiveData<Boolean>()
    val informationConsentStatus: LiveData<Boolean>
        get() = _informationConsentButton


    //유저 아이디 중복 체크
    private val _userIdCheckStatus = MutableLiveData<Boolean>()
    val userIdCheckStatus: LiveData<Boolean>
        get() = _userIdCheckStatus

    //비밀번호 중복 체크
    private val _passwordCheckStatus = MutableLiveData<Boolean>()
    val passwordCheckStatus: LiveData<Boolean>
        get() = _passwordCheckStatus


    //비밀번호 숨기기
    private val _hidePassword = MutableLiveData<Boolean>()
    val hidePassword: LiveData<Boolean>
        get() = _hidePassword

    private val _hideCheckPassword = MutableLiveData<Boolean>()
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

    //아이디
    private val _checkUserId = MutableLiveData("")
    val checkUserId: LiveData<String>
        get() = _checkUserId

    //전화번호
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    //나라
    private val _country = MutableLiveData("")
    val country: LiveData<String>
        get() = _country

    //언어
    private val _language = MutableLiveData("")
    val language: LiveData<String>
        get() = _language

    //생일
    private val _birth = MutableLiveData("")
    val birth: LiveData<String>
        get() = _birth

    fun userId(userId: String){
        _userId.value = userId
    }

    private fun checkUserId(checkUserId: String){
        _checkUserId.value = checkUserId
    }

    fun password(password: String){
        _password.value = password
    }

    fun checkPassword(checkPassword: String){
        _checkPassword.value = checkPassword
    }

    fun phoneNumber(phoneNumber: String){
        _phoneNumber.value = phoneNumber
    }

    fun country(country: String){
        _country.value = country
    }

    fun language(language: String){
        _language.value = language
    }

    fun birth(birth: String){
        _birth.value = birth
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

    fun userIdCheckStatus(status: Boolean?){
        _userIdCheckStatus.value = status
    }

    fun passwordCheckStatus(status: Boolean?){
        _passwordCheckStatus.value = status
    }


    fun checkUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.checkUser(userId)
            }.onSuccess {
                when (it.status) {
                    200 -> {
                        _userIdCheckStatus.value = true
                        checkUserId(userId)
                    }
                    else -> {
                        _userIdCheckStatus.value = false
                        checkUserId(userId)
                    }
                }

            }.onFailure {
                _userIdCheckStatus.value = false
                checkUserId(userId)
            }
        }
    }

    fun signup(){
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signup(SignupReqeustDto(
                    userId = userId.value?:"",
                    password = password.value?:"",
                    phoneNumber = phoneNumber.value?:"",
                    country = country.value?:"",
                    language = language.value?:"",
                    birth = birth.value?:""
                    ))
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