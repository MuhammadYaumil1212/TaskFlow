package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import com.google.firebase.auth.FirebaseUser

sealed interface SignInState {

    data object Loading : SignInState

    data object LoggedOut : SignInState

    data class LoggedIn(
        val user: FirebaseUser
    ) : SignInState
}