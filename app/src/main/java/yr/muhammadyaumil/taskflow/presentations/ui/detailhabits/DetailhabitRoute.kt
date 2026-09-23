package yr.muhammadyaumil.taskflow.presentations.ui.detailhabits

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.TimerHabitRoute

@Serializable
data class DetailhabitRoute(
    val docsId: String
) : NavKey

fun EntryProviderScope<NavKey>.detailHabitRoute(backStack: NavBackStack<NavKey>) {
    entry<DetailhabitRoute> {
        val viewModel: DetailHabitViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(it.docsId) {
            viewModel.getDetailHabit(it.docsId)
        }

        DetailHabitScreen(
            habit = uiState.habit,
            goToTimer = { habit -> backStack.add(TimerHabitRoute(docsId = habit.id)) },
            onLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            onBack = { backStack.removeLastOrNull() }
        )
    }
}

