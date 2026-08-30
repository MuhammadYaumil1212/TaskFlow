package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.models.AuthResult
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val signUpRepository: AuthenticationRepository) :
    ViewModel() {

    private val _authResult = MutableStateFlow<Response<AuthResult>?>(null)
    val authResult: StateFlow<Response<AuthResult>?> = _authResult.asStateFlow()
    fun signUpWithEmailAndPassword(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val cleanPassword = password.trim()
        val cleanConfirm = confirmPassword.trim()
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _authResult.value = Response.Error("Semua kolom harus diisi lengkap.")
            return
        }

        if (cleanPassword != cleanConfirm) {
            _authResult.value = Response.Error("Password dan Konfirmasi Password tidak cocok.")
            return
        }
        viewModelScope.launch {
            _authResult.value = Response.Loading
            val result = signUpRepository.signUpWithEmailAndPassword(
                email = email,
                username = username,
                password = password
            )

            _authResult.value = result
        }
    }

    fun clearAuthState() {
        _authResult.value = null
    }
}