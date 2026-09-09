package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AddTrackerUiState(
    val habitName: String = "",
    val habitNotes: String = "",
    val isHabitNameError: Boolean = false,
    val isHabitNotesError: Boolean = false,
    val isDurationEnabled: Boolean = false,
    val activityDuration: String = "Tidak ada",
    val frequency: String = "Setiap Hari",
    val reminder: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
    val isAttachmentEnabled: Boolean = false,
    val selectedCategoryHex: String = "#9DB499",
    val frequencyExpanded: Boolean = false,
    val showTimePicker: Boolean = false,
    val showDurationPicker: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSavedSuccess: Boolean = false
)