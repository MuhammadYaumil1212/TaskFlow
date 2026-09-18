package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TextSnippet
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.FloatingContainer
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.FloatingItem
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.HabitIcon
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.TimerFocus
import yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components.TitleAndFrequency

@Composable
fun TimerHabitScreen(
    modifier: Modifier = Modifier,
    habit: HabitDto?,
    errorMessage: String?,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Header(onBack = onBack)
                }
                item {
                    TitleAndFrequency(
                        modifier = Modifier.padding(top = 30.dp),
                        title = habit?.name ?: "Unknown",
                        frequency = habit?.frequency ?: "-"
                    )
                }
                item {
                    HabitIcon(
                        isRunning = isRunning,
                        colorIcons = habit?.categoryHex ?: "#FAFAFA"
                    )
                }
                item {
                    TimerFocus(
                        onRunningChanged = { isRunning = it },
                        duration = habit?.duration ?: "00:00:00"
                    )
                }
            }
        }
        FloatingContainer {
            First {
                FloatingItem(
                    icon = Icons.AutoMirrored.Outlined.TextSnippet,
                    onClick = {}
                )
            }

            Second {
                FloatingItem(
                    icon = Icons.Outlined.CloudUpload,
                    onClick = {}
                )
            }
        }
    }
}