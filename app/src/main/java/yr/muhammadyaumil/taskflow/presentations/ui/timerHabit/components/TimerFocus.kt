package yr.muhammadyaumil.taskflow.presentations.ui.timerHabit.components

import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TimerFocus(
    modifier: Modifier = Modifier,
    duration: String,
    onRunningChanged: (Boolean) -> Unit = {}
) {
    val totalDurationMs = remember(duration) {
        parseDurationToMillis(duration)
    }

    var isRunning by rememberSaveable {
        mutableStateOf(false)
    }

    var remainingMs by rememberSaveable {
        mutableLongStateOf(totalDurationMs)
    }

    LaunchedEffect(totalDurationMs) {
        if (!isRunning) {
            remainingMs = totalDurationMs
        }
    }

    LaunchedEffect(isRunning) {
        if (!isRunning) {
            return@LaunchedEffect
        }

        val endTime =
            SystemClock.elapsedRealtime() + remainingMs

        while (isRunning) {
            val currentTime =
                SystemClock.elapsedRealtime()

            val newRemaining =
                endTime - currentTime

            if (newRemaining <= 0L) {
                remainingMs = 0L
                isRunning = false
                onRunningChanged(false)
                break
            }

            remainingMs = newRemaining

            delay(50L)
        }
    }

    val totalSeconds =
        remainingMs / 1000L

    val hours =
        totalSeconds / 3600L

    val minutes =
        (totalSeconds % 3600L) / 60L

    val seconds =
        totalSeconds % 60L

    val timeText =
        String.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = timeText,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF757575)
        )
        Spacer(modifier = Modifier.height(100.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isRunning) {
                    isRunning = false
                    onRunningChanged(false)
                } else {
                    if (remainingMs <= 0L) {
                        remainingMs = totalDurationMs
                    }

                    isRunning = true
                    onRunningChanged(true)
                }
            },
            shape = RoundedCornerShape(
                size = 10.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme
                    .colorScheme
                    .primary
            ),
        ) {
            Text(
                text = if (isRunning) {
                    "Stop"
                } else {
                    "Start"
                },
                color = Color.White
            )
        }
    }
}

private fun parseDurationToMillis(
    duration: String
): Long {
    val parts = duration.split(":")

    if (parts.size != 3) {
        return 0L
    }

    val hours =
        parts[0].toLongOrNull() ?: 0L

    val minutes =
        parts[1].toLongOrNull() ?: 0L

    val seconds =
        parts[2].toLongOrNull() ?: 0L

    return (
            hours * 60L * 60L * 1000L
            ) + (
            minutes * 60L * 1000L
            ) + (
            seconds * 1000L
            )
}