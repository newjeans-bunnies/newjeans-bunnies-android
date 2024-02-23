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
import newjeans.bunnies.auth.state.signup.PhoneNumberCheckState
import newjeans.bunnies.auth.state.signup.SignupState
import newjeans.bunnies.auth.state.signup.UserIdCheckState

import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.SignupReqeustDto

import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private var _signupState = MutableSharedFlow<SignupState>()
    val signupState: SharedFlow<SignupState> = _signupState

    private var _phoneNumberCheckState = MutableSharedFlow<PhoneNumberCheckState>()
    val phoneNumberCheckState: SharedFlow<PhoneNumberCheckState> = _phoneNumberCheckState

    private var _userIdCheckState = MutableSharedFlow<UserIdCheckState>()
    val userIdCheckState: SharedFlow<UserIdCheckState> = _userIdCheckState

    //아이디
    private val _userId = MutableLiveData("")
    val userId: LiveData<String>
        get() = _userId

    //비밀번호
    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

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

    //이용약관 동의
    private val _useAgreementButton = MutableLiveData<Boolean>()
    val useAgreementStatus: LiveData<Boolean>
        get() = _useAgreementButton

    //개인정보 동의
    private val _informationConsentButton = MutableLiveData<Boolean>()
    val informationConsentStatus: LiveData<Boolean>
        get() = _informationConsentButton

    fun userId(userId: String) {
        _userId.value = userId
    }

    fun password(password: String) {
        _password.value = password
    }

    fun birth(birth: String) {
        _birth.value = birth
    }

    fun useAgreementButton(status: Boolean) {
        _useAgreementButton.value = status
    }

    fun informationConsentButton(status: Boolean) {
        _informationConsentButton.value = status
    }


    fun checkUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.checkUserId(userId)
            }.onSuccess {
                _userIdCheckState.emit(UserIdCheckState(true, "", userId))
            }.onFailure { e ->
                if (e.message.toString() == "HTTP 409 ") {
                    _userIdCheckState.emit(UserIdCheckState(false, e.message.toString(), userId))
                } else {
                    Log.d("애러", e.message.toString())
                }
            }
        }
    }

    fun checkPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.checkPhoneNumber(phoneNumber)
            }.onSuccess {
                when (it.status) {
                    200 -> _phoneNumberCheckState.emit(PhoneNumberCheckState(true, "", phoneNumber))
                }
            }.onFailure { e ->
                _phoneNumberCheckState.emit(PhoneNumberCheckState(false, e.message.toString(), ""))
            }
        }
    }

    fun signup() {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signup(
                    SignupReqeustDto(
                        userId = userId.value ?: "",
                        password = password.value ?: "",
                        phoneNumber = phoneNumber.value ?: "",
                        country = country.value ?: "",
                        language = language.value ?: "",
                        birth = birth.value ?: ""
                    )
                )
            }.onSuccess {
                when (it.status) {
                    201 -> _signupState.emit(SignupState(true, ""))
                }
            }.onFailure { e ->
                _signupState.emit(SignupState(false, e.message.toString()))
            }
        }
    }
}