package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.signIn.Repository.SignInRepository
import yr.muhammadyaumil.taskflow.data.signIn.models.LogoutResult
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    private val _logoutResult = MutableStateFlow<Response<LogoutResult>?>(null)
    val logoutResult: StateFlow<Response<LogoutResult>?> = _logoutResult.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            _logoutResult.value = Response.Loading
            val isLoggedOut = signInRepository.logout()
            _logoutResult.value = isLoggedOut
        }
    }
}