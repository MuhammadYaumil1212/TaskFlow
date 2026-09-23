package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class TimerHabitRoute(
    val docsId: String
) : NavKey

fun EntryProviderScope<NavKey>.timerHabitRoute(backStack: NavBackStack<NavKey>) {
    entry<TimerHabitRoute> {
        val viewModel: TimerHabitViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(it.docsId) {
            viewModel.getDetailHabit(it.docsId)
        }

        TimerHabitScreen(
            habit = uiState.habit,
            errorMessage = uiState.errorMessage,
            onBack = { backStack.removeLastOrNull() }
        )
    }
}