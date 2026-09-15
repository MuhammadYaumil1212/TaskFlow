package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import yr.muhammadyaumil.taskflow.data.authentication.models.UserData

data class TrackerUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserData? = null,
    val isLogoutSuccess: Boolean = false
)
