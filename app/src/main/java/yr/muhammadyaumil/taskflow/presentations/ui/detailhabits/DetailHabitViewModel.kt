package yr.muhammadyaumil.taskflow.presentations.ui.detailhabits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.ManageHabits.repository.HabitRepository

@HiltViewModel
class DetailHabitViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailHabitUiState())
    val uiState: StateFlow<DetailHabitUiState> = _uiState.asStateFlow()

    fun getDetailHabit(docsId: String) = viewModelScope.launch {
        when (val detailHabit = habitRepository.getDetailHabit(docsId)) {
            is Response.Loading -> {
                _uiState.update {
                    it.copy(isLoading = true)
                }
            }

            is Response.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        habit = detailHabit.data
                    )
                }
            }

            is Response.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = detailHabit.message
                    )
                }
            }
        }
    }
}