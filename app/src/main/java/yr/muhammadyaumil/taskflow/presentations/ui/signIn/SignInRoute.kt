package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.SignUpRoute

@Serializable
data object SignInRoute : NavKey

fun EntryProviderScope<NavKey>.signInRoute(backStack: NavBackStack<NavKey>) {
    entry<SignInRoute> {
        SignInScreen(
            onSignUpClick = { backStack.add(SignUpRoute) },
            onForgotPassClick = { },
        )
    }
}