package yr.muhammadyaumil.taskflow.presentations.ui.signIn

data class SignInUiState(
    val usernameValue: String = "",
    val passwordValue: String = "",
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignInSuccessful: Boolean = false
)