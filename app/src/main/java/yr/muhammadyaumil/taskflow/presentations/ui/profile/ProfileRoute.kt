package yr.muhammadyaumil.taskflow.presentations.ui.profile

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute : NavKey

fun EntryProviderScope<NavKey>.profileRoute(backStack: NavBackStack<NavKey>) {
    entry<ProfileRoute> {
        ProfileScreen()
    }
}