package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.models.AuthResult
import yr.muhammadyaumil.taskflow.data.authentication.models.LogoutResult
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val signInRepository: AuthenticationRepository
) : ViewModel() {
    private val _logoutResult = MutableStateFlow<Response<LogoutResult>?>(null)
    val logoutResult: StateFlow<Response<LogoutResult>?> = _logoutResult.asStateFlow()

    private val _getUserData = MutableStateFlow<Response<AuthResult>?>(null)
    val getUserData: StateFlow<Response<AuthResult>?> = _getUserData.asStateFlow()

    init {
        getUserDisplayName()
    }

    fun logout() {
        viewModelScope.launch {
            _logoutResult.value = Response.Loading
            val result = signInRepository.logout()
            _logoutResult.value = result

        }
    }

    private fun getUserDisplayName() = viewModelScope.launch {
        _getUserData.value = Response.Loading
        val getUserData = signInRepository.getUserDisplayName()
        _getUserData.value = getUserData
    }
}