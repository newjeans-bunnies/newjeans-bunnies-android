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
import newjeans.bunnies.auth.state.signup.PhoneNumberCertificationState
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

    companion object {
        const val TAG = "SignupViewModel"
    }


    private var _signupState = MutableSharedFlow<SignupState>()
    val signupState: SharedFlow<SignupState> = _signupState

    private var _phoneNumberCheckState = MutableSharedFlow<PhoneNumberCheckState>()
    val phoneNumberCheckState: SharedFlow<PhoneNumberCheckState> = _phoneNumberCheckState

    private var _userIdCheckState = MutableSharedFlow<UserIdCheckState>()
    val userIdCheckState: SharedFlow<UserIdCheckState> = _userIdCheckState


    private val _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String> = _verificationId

    val phoneNumberCertificationState = MutableSharedFlow<PhoneNumberCertificationState>()

    fun verificationId(verificationId: String){
        _verificationId.value = verificationId
    }

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

    //생일
    private val _birth = MutableLiveData("")
    val birth: LiveData<String>
        get() = _birth


    fun checkUser(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.checkUserId(userId)
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
                authRepository.checkPhoneNumber(phoneNumber)
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
                    SignupReqeustDto(
                        userId = userId.value ?: "",
                        password = password.value ?: "",
                        phoneNumber = phoneNumber.value ?: "",
                        country = country.value ?: "",
                        language = country.value ?: "",
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