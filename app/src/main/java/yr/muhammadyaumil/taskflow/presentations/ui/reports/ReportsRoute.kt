package yr.muhammadyaumil.taskflow.presentations.ui.reports

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object ReportsRoute : NavKey

fun EntryProviderScope<NavKey>.reportsRoute(backStack: NavBackStack<NavKey>) {
    entry<ReportsRoute> {
        ReportsScreen()
    }
}