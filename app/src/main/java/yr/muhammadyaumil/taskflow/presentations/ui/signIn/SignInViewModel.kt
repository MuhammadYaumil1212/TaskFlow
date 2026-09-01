package yr.muhammadyaumil.taskflow.presentations.ui.signIn

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
class SignInViewModel @Inject constructor(private val signInRepository: AuthenticationRepository) :
    ViewModel() {
    private val _authResult = MutableStateFlow<Response<AuthResult>?>(null)
    val authResult: StateFlow<Response<AuthResult>?> = _authResult.asStateFlow()

    private val _isSessionActive = MutableStateFlow<Boolean>(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    init {
        isLoggedIn()
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _authResult.value = Response.Loading
            val login = signInRepository.signInWithGoogle()
            _authResult.value = login
        }
    }

    fun signInWithUsernameAndPassword(
        username: String,
        password: String
    ) {
        if (username.isBlank() || password.isBlank()) {
            _authResult.value = Response.Error("Semua kolom harus diisi lengkap.")
            return
        }
        viewModelScope.launch {
            _authResult.value = Response.Loading
            val signInWithUsername = signInRepository.signInWithUsernameAndPassword(
                username = username,
                password = password
            )
            _authResult.value = signInWithUsername
        }
    }

    private fun isLoggedIn() {
        _isSessionActive.value = signInRepository.isLoggedIn()
    }
}