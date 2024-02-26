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

import newjeans.bunnies.auth.state.login.CheckSupportState
import newjeans.bunnies.main.viewmodel.UserViewModel
import newjeans.bunnies.network.auth.AuthRepository

import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _checkSupportState = MutableSharedFlow<CheckSupportState>()
    val checkSupportState: SharedFlow<CheckSupportState> = _checkSupportState

    private var _countrys = MutableLiveData<List<String>>()
    val countrys: LiveData<List<String>> = _countrys

    fun checkSupport() {
        viewModelScope.launch {
            val countrys = countrys.value
            if (countrys.isNullOrEmpty()) {
                kotlin.runCatching {
                    authRepository.checkSupport()
                }.onSuccess {
                    _countrys.value = it.country
                    _checkSupportState.emit(CheckSupportState(true))
                }.onFailure { e ->
                    _checkSupportState.emit(CheckSupportState(false, e.message.toString()))
                    Log.d(UserViewModel.TAG, e.message.toString())
                }
            } else {
                _checkSupportState.emit(CheckSupportState(true))
            }
        }
    }
}