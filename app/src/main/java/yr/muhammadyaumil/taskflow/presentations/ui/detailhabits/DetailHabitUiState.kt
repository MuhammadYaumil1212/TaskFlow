package yr.muhammadyaumil.taskflow.presentations.ui.detailhabits

import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

data class DetailHabitUiState(
    val isLoading: Boolean = false,
    val docsId: String? = null,
    val habit: HabitDto? = null,
    val errorMessage: String? = null
)