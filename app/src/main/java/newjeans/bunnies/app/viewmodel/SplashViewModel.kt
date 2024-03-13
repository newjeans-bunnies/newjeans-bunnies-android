package newjeans.bunnies.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import newjeans.bunnies.auth.state.ReissueTokenState
import newjeans.bunnies.auth.state.UserDetailInformationState
import newjeans.bunnies.auth.viewmodel.AuthViewModel
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.user.UserRepository
import javax.inject.Inject


private const val TAG = "SplashViewModel"
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _getUserDetailInformationState = MutableSharedFlow<UserDetailInformationState>()
    val getUserDetailInformationState: SharedFlow<UserDetailInformationState> = _getUserDetailInformationState

    private val _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState
    fun getUserDetailInformation(authorization: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUserDetails("Bearer $authorization")
            }.onSuccess {
                Log.d(TAG, it.toString())
                prefs.userId = it.id
                prefs.userPhoneNumber = it.phoneNumber
                prefs.userImage = it.imageUrl
                _getUserDetailInformationState.emit(UserDetailInformationState(true, ""))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                if (prefs.refreshToken.isNotEmpty()) {
                    reissueToken(
                        prefs.accessToken,
                        prefs.refreshToken,
                        prefs
                    )
                } else {
                    _getUserDetailInformationState.emit(
                        UserDetailInformationState(
                            false,
                            e.message.toString()
                        )
                    )
                }
            }
        }
    }

    private fun reissueToken(
        accessToken: String,
        refreshToken: String,
        prefs: PreferenceManager
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.reissueToken(refreshToken, accessToken)
            }.onSuccess {
                prefs.accessToken = it.accessToken
                prefs.expiredAt = it.expiredAt
                prefs.refreshToken = it.refreshToken
                _reissueTokenState.emit(ReissueTokenState(true, ""))
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _reissueTokenState.emit(ReissueTokenState(false, e.message.toString()))
            }
        }
    }
}