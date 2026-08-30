package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute

@Serializable
data object SignUpRoute : NavKey

fun EntryProviderScope<NavKey>.signUpRoute(backStack: NavBackStack<NavKey>) {

    entry<SignUpRoute> {
        val viewModel: SignUpViewModel = hiltViewModel()
        val authState by viewModel.authResult.collectAsStateWithLifecycle()
        var usernameText by remember { mutableStateOf("") }
        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var confirmPasswordText by remember { mutableStateOf("") }

        if (authState is Response.Success) {
            backStack.clear()
            backStack.add(TrackerRoute)
        }

        SignUpScreen(
            authState = authState,
            emailText = emailText,
            usernameText = usernameText,
            passwordText = passwordText,
            confirmPasswordText = confirmPasswordText,
            emailValueChanged = { emailText = it },
            usernameValueChanged = { usernameText = it },
            passwordValueChanged = { passwordText = it },
            confirmPasswordValueChanged = { confirmPasswordText = it },
            onClearError = {
                viewModel.clearAuthState()
            },
            onTapSignUp = {
                viewModel.signUpWithEmailAndPassword(
                    username = usernameText,
                    email = emailText,
                    password = passwordText,
                    confirmPassword = confirmPasswordText
                )
            }
        )
    }
}
