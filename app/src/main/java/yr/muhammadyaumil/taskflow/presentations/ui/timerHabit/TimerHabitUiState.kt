package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit

import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

data class TimerHabitUiState(
    val isLoading: Boolean = false,
    val habit: HabitDto? = null,
    val errorMessage: String? = null
)
