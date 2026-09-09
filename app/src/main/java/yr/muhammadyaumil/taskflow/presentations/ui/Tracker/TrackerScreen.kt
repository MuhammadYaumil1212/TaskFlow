package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.models.AuthResult
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.components.HabitItem
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.Tracker.components.HorizontalDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TrackerScreen(
    authState: Response<AuthResult>?,
    modifier: Modifier = Modifier,
    goToProfile: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val localeID = remember { Locale("id", "ID") }
    var dayName by remember {
        mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE", localeID)))
    }

    LaunchedEffect(authState) {
        if (authState is Response.Error) {
            snackbarHostState.showSnackbar(
                message = authState.message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 10.dp),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 160.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Header(
                        goToProfile = goToProfile,
                        displayName = if (authState is Response.Success) authState.data.userData?.username else null,
                        dayName = dayName
                    )
                }
                item {
                    HorizontalDatePicker { localDate ->
                        dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", localeID))
                    }
                }
                item {
                    Text(
                        text = "Kegiatan Terdekat",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
                items(10) {
                    HabitItem()
                }
            }

            if (authState is Response.Loading) {
                LoadingSpinner()
            }
        }
    }
}