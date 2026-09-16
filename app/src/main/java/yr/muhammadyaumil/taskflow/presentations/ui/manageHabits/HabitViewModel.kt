package yr.muhammadyaumil.taskflow.presentations.ui.manageHabits

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.ManageHabits.repository.HabitRepository
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val habitRepository: HabitRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HabitUiState())
    val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()

    init {
        getHabit()
    }

    fun getHabit() = viewModelScope.launch {
        habitRepository.getHabit().collect { response ->
            when (response) {
                is Response.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true, isRefreshing = true, errorMessage = null)
                    }
                }

                is Response.Success -> {
                    _uiState.update {
                        val rawHabits = response.data

                        val today = Calendar.getInstance()

                        val sortedAllList = rawHabits.sortedBy { habit ->
                            habit.reminder
                        }

                        val todayList = sortedAllList.filter { habit ->
                            val habitDate = Calendar.getInstance().apply {
                                timeInMillis = habit.date
                            }

                            habitDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                    habitDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                                    habitDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
                        }

                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            todayHabits = todayList,
                            allHabits = sortedAllList
                        )
                    }
                }

                is Response.Error -> {
                    Log.e("FIRESTORE_ERROR", "Gagal load data: ${response.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }
}