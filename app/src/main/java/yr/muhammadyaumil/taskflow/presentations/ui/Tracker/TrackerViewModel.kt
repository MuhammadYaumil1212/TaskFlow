package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import javax.inject.Inject


@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackerUiState())
    val uiState: StateFlow<TrackerUiState> = _uiState.asStateFlow()

    init {
        fetchUserData()
    }

    fun logout() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        when (val result = authenticationRepository.logout()) {
            is Response.Success -> {
                _uiState.update {
                    it.copy(isLoading = false, isLogoutSuccess = true)
                }
            }

            is Response.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }

            else -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun fetchUserData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        when (val response = authenticationRepository.getUserData()) {
            is Response.Success -> {
                _uiState.update {
                    it.copy(isLoading = false, userData = response.data)
                }
            }

            is Response.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = response.message)
                }
            }

            else -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}