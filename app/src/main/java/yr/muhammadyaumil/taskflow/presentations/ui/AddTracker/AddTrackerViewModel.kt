package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.addTracker.repository.HabitRepository
import javax.inject.Inject

@HiltViewModel
class AddTrackerViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTrackerUiState())
    val uiState: StateFlow<AddTrackerUiState> = _uiState.asStateFlow()
    fun onHabitNameChange(name: String) = _uiState.update { it.copy(habitName = name) }
    fun onHabitNotesChange(notes: String) = _uiState.update { it.copy(habitNotes = notes) }
    fun onDurationEnabledChange(enabled: Boolean) =
        _uiState.update { it.copy(isDurationEnabled = enabled) }

    fun onActivityDurationChange(duration: String) =
        _uiState.update { it.copy(activityDuration = duration) }

    fun onAttachmentEnabledChange(enabled: Boolean) =
        _uiState.update { it.copy(isAttachmentEnabled = enabled) }

    fun onFrequencyChange(frequency: String) = _uiState.update { it.copy(frequency = frequency) }
    fun onReminderChange(reminder: String) = _uiState.update { it.copy(reminder = reminder) }
    fun onCategoryChange(hex: String) = _uiState.update { it.copy(selectedCategoryHex = hex) }
    fun resetMessage() = _uiState.update { it.copy(errorMessage = null, isSavedSuccess = false) }
    fun onFrequencyExpandedChange(expanded: Boolean) =
        _uiState.update { it.copy(frequencyExpanded = expanded) }

    fun onShowTimePickerChange(show: Boolean) = _uiState.update { it.copy(showTimePicker = show) }
    fun onShowDurationPickerChange(show: Boolean) =
        _uiState.update { it.copy(showDurationPicker = show) }

    fun saveHabit() {
        val currentState = _uiState.value

        if (currentState.habitName.isBlank()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Nama kegiatan tidak boleh kosong",
                    isHabitNameError = true
                )
            }
            return
        }

        if (currentState.habitNotes.isBlank()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Notes Tidak Boleh Kosong",
                    isHabitNotesError = true
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = habitRepository.createNewHabit(
                name = currentState.habitName,
                notes = currentState.habitNotes,
                isDurationEnabled = currentState.isDurationEnabled,
                duration = currentState.activityDuration,
                frequency = currentState.frequency,
                reminder = currentState.reminder,
                isAttachmentEnabled = currentState.isAttachmentEnabled,
                categoryHex = currentState.selectedCategoryHex
            )

            when (result) {
                is Response.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, isSavedSuccess = true)
                    }
                }

                is Response.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }

                else -> {}
            }
        }
    }
}