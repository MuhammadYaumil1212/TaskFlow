package yr.muhammadyaumil.taskflow.presentations.ui.ManageTasks

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object TasksRoute : NavKey

fun EntryProviderScope<NavKey>.tasksRoute(backStackEntry: NavBackStack<NavKey>) {
    entry<TasksRoute> {
        ManageTasks()
    }
}