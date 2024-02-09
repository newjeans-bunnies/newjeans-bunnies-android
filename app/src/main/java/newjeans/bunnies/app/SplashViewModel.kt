package newjeans.bunnies.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import newjeans.bunnies.app.state.ReissueTokenState
import newjeans.bunnies.data.PreferenceManager
import newjeans.bunnies.network.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _reissueTokenState = MutableSharedFlow<ReissueTokenState>()
    val reissueTokenState: SharedFlow<ReissueTokenState> = _reissueTokenState
    fun reissueToken(refreshToken: String, prefs: PreferenceManager) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.refresh(refreshToken)
            }.onSuccess {
                prefs.accessToken = it.accessToken
                prefs.expiredAt = it.expirationTime
                prefs.refreshToken = it.refreshToken
                _reissueTokenState.emit(ReissueTokenState(isSuccess = true))
            }.onFailure { e ->
                _reissueTokenState.emit(ReissueTokenState(error = e.message.toString()))
            }
        }

    }
}