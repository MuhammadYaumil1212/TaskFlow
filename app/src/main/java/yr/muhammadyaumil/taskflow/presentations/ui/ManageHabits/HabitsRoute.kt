package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HabitsRoute : NavKey

fun EntryProviderScope<NavKey>.habitsRoute(backstack: NavBackStack<NavKey>) {
    entry<HabitsRoute> {
        val viewModel: HabitViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        
        ManageHabits(
            isLoading = uiState.isLoading,
            todayHabitList = uiState.todayHabits,
            allHabitList = uiState.allHabits
        )
    }
}