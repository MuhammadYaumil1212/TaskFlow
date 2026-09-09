package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.TrackerRoute

@Serializable
data object AddTrackerRoute : NavKey

fun EntryProviderScope<NavKey>.addTrackerRoute(backStackEntry: NavBackStack<NavKey>) {
    entry<AddTrackerRoute> {
        val viewModel: AddTrackerViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(uiState.isSavedSuccess) {
            if (uiState.isSavedSuccess) {
                viewModel.resetMessage()
                backStackEntry.clear()
                backStackEntry.add(TrackerRoute)
            }
        }

        AddTrackerScreen(
            habitName = uiState.habitName,
            habitNotes = uiState.habitNotes,
            isHabitNameError = uiState.isHabitNameError,
            isHabitNotesError = uiState.isHabitNotesError,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            isDurationEnabled = uiState.isDurationEnabled,
            activityDuration = uiState.activityDuration,
            isAttachmentEnabled = uiState.isAttachmentEnabled,
            frequency = uiState.frequency,
            reminder = uiState.reminder,
            selectedCategoryHex = uiState.selectedCategoryHex,
            frequencyExpanded = uiState.frequencyExpanded,
            showTimePicker = uiState.showTimePicker,
            showDurationPicker = uiState.showDurationPicker,
            onBack = {
                backStackEntry.clear()
                backStackEntry.add(TrackerRoute)
            },
            onSave = viewModel::saveHabit,
            onHabitNameChange = viewModel::onHabitNameChange,
            onHabitNotesChange = viewModel::onHabitNotesChange,
            onDurationEnabledChange = viewModel::onDurationEnabledChange,
            onActivityDurationChange = viewModel::onActivityDurationChange,
            onAttachmentEnabledChange = viewModel::onAttachmentEnabledChange,
            onClearError = viewModel::resetMessage,
            onFrequencyChange = viewModel::onFrequencyChange,
            onReminderChange = viewModel::onReminderChange,
            onCategoryChange = viewModel::onCategoryChange,
            onFrequencyExpandedChange = viewModel::onFrequencyExpandedChange,
            onShowTimePickerChange = viewModel::onShowTimePickerChange,
            onShowDurationPickerChange = viewModel::onShowDurationPickerChange,
        )
    }
}