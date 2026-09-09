package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute

@Serializable
data object SignUpRoute : NavKey

fun EntryProviderScope<NavKey>.signUpRoute(backStack: NavBackStack<NavKey>) {
    entry<SignUpRoute> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.isSignUpSuccessful) {
            if (uiState.isSignUpSuccessful) {
                viewModel.resetSuccessState()
                backStack.clear()
                backStack.add(TrackerRoute)
            }
        }

        SignUpScreen(
            usernameText = uiState.usernameValue,
            emailText = uiState.emailValue,
            passwordText = uiState.passwordValue,
            confirmPasswordText = uiState.confirmPasswordValue,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            isUsernameError = uiState.isUsernameError,
            isEmailError = uiState.isEmailError,
            isPasswordError = uiState.isPasswordError,
            isConfirmPasswordError = uiState.isConfirmPasswordError,
            emailValueChanged = viewModel::onEmailChange,
            usernameValueChanged = viewModel::onUsernameChange,
            passwordValueChanged = viewModel::onPasswordChange,
            confirmPasswordValueChanged = viewModel::onConfirmPasswordChange,
            onClearError = viewModel::resetMessage,
            onTapSignUp = viewModel::signUpWithEmailAndPassword
        )
    }
}