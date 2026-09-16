package yr.muhammadyaumil.taskflow.presentations.ui.manageHabits

import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

data class HabitUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = "",
    val todayHabits: List<HabitDto> = emptyList(),
    val allHabits: List<HabitDto> = emptyList(),
)
