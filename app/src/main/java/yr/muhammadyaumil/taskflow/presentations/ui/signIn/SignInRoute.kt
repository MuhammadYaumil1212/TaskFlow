package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.SignUpRoute

@Serializable
data object SignInRoute : NavKey

fun EntryProviderScope<NavKey>.signInRoute(
    backStack: NavBackStack<NavKey>
) {
    entry<SignInRoute> {

        val viewModel: SignInViewModel = hiltViewModel()
        val isLoggedIn by viewModel.isSessionActive.collectAsStateWithLifecycle()
        val authResult by viewModel.authResult.collectAsStateWithLifecycle()

        LaunchedEffect(isLoggedIn) {
            if (isLoggedIn) {
                backStack.clear()
                backStack.add(TrackerRoute)
            }
        }

        LaunchedEffect(authResult) {
            if (authResult is Response.Success) {
                backStack.clear()
                backStack.add(TrackerRoute)
            }
        }

        SignInScreen(
            authState = authResult,
            onSignUpClick = {
                backStack.add(SignUpRoute)
            },

            onTapGoogleLogin = {
                viewModel.signInWithGoogle()
            },

            onForgotPassClick = {
                // TODO
            }
        )
    }
}