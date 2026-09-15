package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

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
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackerUiState())
    val uiState: StateFlow<TrackerUiState> = _uiState.asStateFlow()

    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    init {
        fetchUserData()
        checkSession()
        getHabitData()
    }

    fun logout() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        when (val result = authenticationRepository.logout()) {
            is Response.Success -> {
                _uiState.update {
                    it.copy(isLoading = false, isLogoutSuccess = true)
                }
            }

            is Response.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }

            else -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            when (val result = authenticationRepository.isLoggedIn()) {
                is Response.Success -> {
                    _isSessionActive.value = result.data
                }

                is Response.Error -> {
                    _isSessionActive.value = false
                }

                else -> {}
            }
        }
    }

    private fun fetchUserData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        when (val response = authenticationRepository.getUserData()) {
            is Response.Success -> {
                _uiState.update {
                    it.copy(isLoading = false, userData = response.data)
                }
            }

            is Response.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = response.message)
                }
            }

            else -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getHabitData() = viewModelScope.launch {
        habitRepository.getHabit().collect { response ->
            when (response) {
                is Response.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Response.Success -> {
                    val rawHabits = response.data

                    val today = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }

                    val threeDaysLater = today.clone() as Calendar
                    threeDaysLater.add(Calendar.DAY_OF_YEAR, 3)

                    val nearestHabits = rawHabits
                        .filter { habit ->
                            val habitDate = Calendar.getInstance().apply {
                                timeInMillis = habit.date
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }

                            habitDate.timeInMillis in
                                    today.timeInMillis..threeDaysLater.timeInMillis
                        }
                        .sortedBy { habit ->
                            habit.date
                        }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            nearestHabitList = nearestHabits
                        )
                    }
                }

                is Response.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = response.message)
                    }
                }
            }
        }
    }
}