package yr.muhammadyaumil.taskflow.presentations.ui.signUp

data class SignUpUiState(
    val usernameValue: String = "",
    val emailValue: String = "",
    val passwordValue: String = "",
    val confirmPasswordValue: String = "",
    val isUsernameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfirmPasswordError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpSuccessful: Boolean = false
)