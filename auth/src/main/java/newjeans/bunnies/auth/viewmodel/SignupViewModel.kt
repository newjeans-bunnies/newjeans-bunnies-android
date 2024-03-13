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
import newjeans.bunnies.auth.state.signup.CertificationNumberVerifyState
import newjeans.bunnies.auth.state.signup.PhoneNumberCertificationState
import newjeans.bunnies.auth.state.signup.PhoneNumberCheckState
import newjeans.bunnies.auth.state.signup.SignupState
import newjeans.bunnies.auth.state.signup.UserIdCheckState

import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.auth.dto.reqeust.CertificationVerifyRequestDto
import newjeans.bunnies.network.auth.dto.reqeust.SignupRequestDto
import newjeans.bunnies.network.user.UserRepository

import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        const val TAG = "SignupViewModel"
    }


    private var _signupState = MutableSharedFlow<SignupState>()
    val signupState: SharedFlow<SignupState> = _signupState

    private var _phoneNumberCheckState = MutableSharedFlow<PhoneNumberCheckState>()
    val phoneNumberCheckState: SharedFlow<PhoneNumberCheckState> = _phoneNumberCheckState

    private var _userIdCheckState = MutableSharedFlow<UserIdCheckState>()
    val userIdCheckState: SharedFlow<UserIdCheckState> = _userIdCheckState

    private var _phoneNumberCertificationState = MutableSharedFlow<PhoneNumberCertificationState>()
    val phoneNumberCertificationState: SharedFlow<PhoneNumberCertificationState> =
        _phoneNumberCertificationState

    private var _certificationNumberVerifyState =
        MutableSharedFlow<CertificationNumberVerifyState>()
    val certificationNumberVerifyState: SharedFlow<CertificationNumberVerifyState> =
        _certificationNumberVerifyState


    private val _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String> = _verificationId

    fun verificationId(verificationId: String) {
        _verificationId.value = verificationId
    }


    private val _userId = MutableLiveData("")
    val userId: LiveData<String>
        get() = _userId

    //비밀번호
    private val _password = MutableLiveData("")
    val password: LiveData<String>
        get() = _password

    //비밀번호
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    //나라
    private val _country = MutableLiveData("")
    val country: LiveData<String>
        get() = _country

    //생일
    private val _birth = MutableLiveData("")
    val birth: LiveData<String>
        get() = _birth

    fun password(password: String) {
        _password.value = password
    }

    fun birth(birth: String) {
        _birth.value = birth
    }

    fun country(country: String) {
        _country.value = country
    }

    fun userId(userId: String) {
        _userId.value = userId
    }

    fun phoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun deleteData(){
        _userId.value = ""
        _password.value = ""
        _phoneNumber.value = ""
        _country.value = ""
        _birth.value = ""
    }


    fun checkUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.checkUserId(userId)
            }.onSuccess {
                _userIdCheckState.emit(UserIdCheckState(true, "", userId))
            }.onFailure { e ->
                if (e.message.toString() == "HTTP 409 ") _userIdCheckState.emit(
                    UserIdCheckState(
                        false, e.message.toString(), userId
                    )
                )
                else Log.d(TAG, e.message.toString())
            }
        }
    }

    fun checkPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.checkPhoneNumber(phoneNumber)
            }.onSuccess {
                _phoneNumberCheckState.emit(PhoneNumberCheckState(true, "", phoneNumber))
            }.onFailure { e ->
                if (e.message.toString() == "HTTP 409 ") _phoneNumberCheckState.emit(
                    PhoneNumberCheckState(
                        false, e.message.toString(), ""
                    )
                )
                else Log.d(TAG, e.message.toString())
            }
        }
    }

    fun signup() {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signup(
                    SignupRequestDto(
                        userId = userId.value?:"",
                        password = password.value?:"",
                        phoneNumber = phoneNumber.value?:"",
                        country = country.value?:"",
                        language = country.value?:"",
                        birth = birth.value?:"",
                        authority = "USER"
                    )
                )
            }.onSuccess {
                when (it.status) {
                    201 -> _signupState.emit(SignupState(true, ""))
                }
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _signupState.emit(SignupState(false, e.message.toString()))
            }
        }
    }

    //인증번호 확인 api
    fun verify(certificationNumber: String, phoneNumber: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.verify(
                    CertificationVerifyRequestDto(
                        certificationNumber = certificationNumber,
                        phoneNumber = phoneNumber,
                    )
                )
            }.onSuccess {
                _certificationNumberVerifyState.emit(CertificationNumberVerifyState(true))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _certificationNumberVerifyState.emit(
                    CertificationNumberVerifyState(
                        false,
                        e.message.toString()
                    )
                )
            }
        }
    }

    //인증번호 받기 api
    fun certification(phoneNumber: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.certification(phoneNumber)
            }.onSuccess {
                _phoneNumberCertificationState.emit(PhoneNumberCertificationState(true))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _phoneNumberCertificationState.emit(
                    PhoneNumberCertificationState(
                        false,
                        e.message.toString()
                    )
                )
            }
        }
    }
}