package yr.muhammadyaumil.taskflow.presentations.ui.detailhabits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.core.helper.formatDurationString
import yr.muhammadyaumil.taskflow.core.helper.formatLongToDateLegacy
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.BannerStreak
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.InfoGridCard
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.InfoItem
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.NoMoment
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.NotesArea
import yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components.PlayAndDelete

@Composable
fun DetailHabitScreen(
    modifier: Modifier = Modifier,
    habit: HabitDto?,
    onLoading: Boolean = false,
    goToTimer: (HabitDto) -> Unit,
    errorMessage: String?,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val habitInfoItems = listOf(
        InfoItem(label = "Durasi", value = formatDurationString(habit?.duration)),
        InfoItem(label = "Pengingat", value = habit?.reminder ?: "00:00"),
        InfoItem(
            label = "Tanggal",
            value = formatLongToDateLegacy(habit?.date, pattern = "d/M/yyyy")
        )
    )
    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            Header(
                modifier = Modifier.padding(16.dp),
                onBack = onBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                    PlayAndDelete(
                        habit = habit,
                        onPlay = {
                            if (habit != null) {
                                goToTimer(habit)
                            }
                        },
                        onDelete = {}
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
                item {
                    InfoGridCard(items = habitInfoItems)
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                    BannerStreak(
                        text = "7 Hari Streak",
                        description = "Terus jaga konsistensi"
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                    NotesArea(notes = habit?.notes ?: "Tidak ada Catatan")
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                    NoMoment()
//                    MomentsArea()
                }
            }
        }

        if (onLoading) {
            LoadingSpinner()
        }
    }
}