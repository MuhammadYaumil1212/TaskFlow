package yr.muhammadyaumil.taskflow.presentations.ui.signUp

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
class SignUpViewModel @Inject constructor(
    private val signUpRepository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) =
        _uiState.update { it.copy(usernameValue = username, isUsernameError = false) }

    fun onEmailChange(email: String) =
        _uiState.update { it.copy(emailValue = email, isEmailError = false) }

    fun onPasswordChange(password: String) =
        _uiState.update { it.copy(passwordValue = password, isPasswordError = false) }

    fun onConfirmPasswordChange(confirmPassword: String) = _uiState.update {
        it.copy(
            confirmPasswordValue = confirmPassword,
            isConfirmPasswordError = false
        )
    }

    fun resetMessage() = _uiState.update { it.copy(errorMessage = null) }

    fun resetSuccessState() = _uiState.update { it.copy(isSignUpSuccessful = false) }

    fun signUpWithEmailAndPassword() {
        val currentState = _uiState.value
        val cleanPassword = currentState.passwordValue.trim()
        val cleanConfirm = currentState.confirmPasswordValue.trim()

        val isUsernameBlank = currentState.usernameValue.isBlank()
        val isEmailBlank = currentState.emailValue.isBlank()
        val isPasswordBlank = currentState.passwordValue.isBlank()
        val isConfirmBlank = currentState.confirmPasswordValue.isBlank()
        val isPasswordMismatch = cleanPassword != cleanConfirm

        if (isUsernameBlank || isEmailBlank || isPasswordBlank || isConfirmBlank) {
            _uiState.update {
                it.copy(
                    isUsernameError = isUsernameBlank,
                    isEmailError = isEmailBlank,
                    isPasswordError = isPasswordBlank,
                    isConfirmPasswordError = isConfirmBlank,
                    errorMessage = "Semua kolom harus diisi lengkap."
                )
            }
            return
        }

        if (isPasswordMismatch) {
            _uiState.update {
                it.copy(
                    isPasswordError = true,
                    isConfirmPasswordError = true,
                    errorMessage = "Password dan Konfirmasi Password tidak cocok."
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = signUpRepository.signUpWithEmailAndPassword(
                email = currentState.emailValue,
                username = currentState.usernameValue,
                password = currentState.passwordValue
            )) {
                is Response.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSignUpSuccessful = true) }
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