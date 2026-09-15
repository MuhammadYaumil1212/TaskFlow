package yr.muhammadyaumil.taskflow.presentations.ui.ManageHabits

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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
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
        Log.d("GETTING HABIT", "habit called")
        habitRepository.getHabit().collect { response ->
            when (response) {
                is Response.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true, errorMessage = null)
                    }
                }

                is Response.Success -> {
                    Log.d("FIRESTORE_SUCCESS", "Berhasil load data : ${response.data.size}")
                    _uiState.update {
                        val rawHabits = response.data
                        val currentTime =
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        val sortedAllList = rawHabits.sortedBy { habit ->
                            habit.reminder
                        }
                        val todayList = sortedAllList.filter { habit ->
                            habit.reminder >= currentTime
                        }
                        it.copy(
                            isLoading = false,
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
                            errorMessage = response.message
                        )
                    }
                }
            }
        }

    }

    private fun getStartOfDay(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}