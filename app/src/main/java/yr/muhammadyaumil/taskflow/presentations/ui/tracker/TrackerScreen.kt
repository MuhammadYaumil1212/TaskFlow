package yr.muhammadyaumil.taskflow.presentations.ui.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import yr.muhammadyaumil.taskflow.core.color.toComposeColor
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import yr.muhammadyaumil.taskflow.data.authentication.models.UserData
import yr.muhammadyaumil.taskflow.presentations.ui.tracker.components.HabitItem
import yr.muhammadyaumil.taskflow.presentations.ui.tracker.components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.tracker.components.HorizontalDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TrackerScreen(
    userData: UserData?,
    errorMessage: String?,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    todayHabits: List<HabitDto>,
    modifier: Modifier = Modifier,
    goToProfile: () -> Unit,
    onClick: (HabitDto) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = rememberPullToRefreshState()
    val localeID = remember { Locale("id", "ID") }
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val dayName = selectedDate.format(
        DateTimeFormatter.ofPattern("EEEE", localeID)
    )

    val selectedDateHabits = todayHabits.filter { habit ->
        val habitDate = Instant
            .ofEpochMilli(habit.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        habitDate == selectedDate
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = state,
            modifier = Modifier.fillMaxSize()
        ) {
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
                        displayName = userData?.username,
                        dayName = dayName
                    )
                }

                item {
                    HorizontalDatePicker { localDate ->
                        selectedDate = localDate
                    }
                }

                item {
                    Text(
                        text = "Kegiatan ${
                            selectedDate.format(
                                DateTimeFormatter.ofPattern(
                                    "d MMMM yyyy",
                                    localeID
                                )
                            )
                        }",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }

                items(
                    items = selectedDateHabits,
                    key = { habit -> habit.id }
                ) { habit ->
                    HabitItem(
                        onClick = { onClick(habit) },
                        title = habit.name,
                        subtitle = habit.notes,
                        color = habit.categoryHex.toComposeColor(
                            defaultColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    }
}
