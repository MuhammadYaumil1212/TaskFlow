package yr.muhammadyaumil.taskflow.presentations.ui.settings

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object SettingsRoute : NavKey

fun EntryProviderScope<NavKey>.settingsRoute(backStack: NavBackStack<NavKey>) {
    entry<SettingsRoute> {
        SettingsScreen()
    }
}