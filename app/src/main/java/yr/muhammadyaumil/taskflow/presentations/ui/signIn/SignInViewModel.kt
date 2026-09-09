package yr.muhammadyaumil.taskflow.presentations.ui.signIn

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
class SignInViewModel @Inject constructor(
    private val signInRepository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    init {
        checkSession()
    }

    fun onUsernameChange(username: String) = _uiState.update {
        it.copy(usernameValue = username, isUsernameError = false)
    }

    fun onPasswordChange(password: String) = _uiState.update {
        it.copy(passwordValue = password, isPasswordError = false)
    }

    fun resetMessage() = _uiState.update { it.copy(errorMessage = null) }

    fun resetSuccessState() = _uiState.update { it.copy(isSignInSuccessful = false) }

    private fun checkSession() {
        _isSessionActive.value = signInRepository.isLoggedIn()
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = signInRepository.signInWithGoogle()) {
                is Response.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSignInSuccessful = true) }
                }

                is Response.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }

                is Response.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun signInWithUsernameAndPassword() {
        val currentState = _uiState.value

        if (currentState.usernameValue.isBlank() || currentState.passwordValue.isBlank()) {
            _uiState.update {
                it.copy(
                    isUsernameError = currentState.usernameValue.isBlank(),
                    isPasswordError = currentState.passwordValue.isBlank(),
                    errorMessage = "Username dan password tidak boleh kosong"
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = signInRepository.signInWithUsernameAndPassword(
                username = currentState.usernameValue,
                password = currentState.passwordValue
            )) {
                is Response.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSignInSuccessful = true) }
                }

                is Response.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }

                is Response.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}