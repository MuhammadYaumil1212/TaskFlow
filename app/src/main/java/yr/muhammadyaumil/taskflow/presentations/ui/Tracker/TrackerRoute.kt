package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.presentations.ui.profile.ProfileRoute
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.SignInRoute

@Serializable
data object TrackerRoute : NavKey

fun EntryProviderScope<NavKey>.trackerRoute(backStack: NavBackStack<NavKey>) {

    entry<TrackerRoute> {
        val viewModel: TrackerViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.isLogoutSuccess) {
            if (uiState.isLogoutSuccess) {
                backStack.clear()
                backStack.add(SignInRoute)
            }
        }

        TrackerScreen(
            userData = uiState.userData,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            goToProfile = { backStack.add(ProfileRoute) }
        )
    }
}