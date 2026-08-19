package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute : NavKey

fun EntryProviderScope<NavKey>.signUpRoute(backStack: NavBackStack<NavKey>) {
    entry<SignUpRoute> { SignUpScreen() }
}
