package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.SignUpRoute

@Serializable
data object SignInRoute : NavKey

fun EntryProviderScope<NavKey>.signInRoute(
    backStack: NavBackStack<NavKey>
) {
    entry<SignInRoute> {

        val viewModel: SignInViewModel = hiltViewModel()

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val isLoggedIn by viewModel.isSessionActive.collectAsStateWithLifecycle()

        LaunchedEffect(isLoggedIn, uiState.isSignInSuccessful) {
            if (isLoggedIn || uiState.isSignInSuccessful) {
                viewModel.resetSuccessState()
                backStack.clear()
                backStack.add(TrackerRoute)
            }
        }

        SignInScreen(
            usernameText = uiState.usernameValue,
            passwordText = uiState.passwordValue,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            isUsernameError = uiState.isUsernameError,
            isPasswordError = uiState.isPasswordError,
            usernameValueChanged = viewModel::onUsernameChange,
            passwordValueChanged = viewModel::onPasswordChange,
            onClearError = viewModel::resetMessage,
            onSignInClick = viewModel::signInWithUsernameAndPassword,
            onTapGoogleLogin = viewModel::signInWithGoogle,
            onSignUpClick = {
                backStack.add(SignUpRoute)
            },
            onForgotPassClick = {
                // TODO
            }
        )
    }
}