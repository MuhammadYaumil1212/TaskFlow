package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object HabitsRoute : NavKey

fun EntryProviderScope<NavKey>.habitsRoute(backstack: NavBackStack<NavKey>) {
    entry<HabitsRoute> {
        ManageHabits()
    }
}