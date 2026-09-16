package yr.muhammadyaumil.taskflow.presentations.ui.tracker

import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import yr.muhammadyaumil.taskflow.data.authentication.models.UserData

data class TrackerUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserData? = null,
    val isRefreshing: Boolean = false,
    val isLogoutSuccess: Boolean = false,
    val nearestHabitList: List<HabitDto> = emptyList()
)
