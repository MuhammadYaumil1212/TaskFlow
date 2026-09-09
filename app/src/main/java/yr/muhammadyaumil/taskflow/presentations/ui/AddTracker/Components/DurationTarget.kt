package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DurationTarget(
    modifier: Modifier = Modifier,
    isDurationEnabled: Boolean,
    onDurationEnabledChange: (Boolean) -> Unit,
    activityDuration: String,
    onShowDurationPickerChange: (Boolean) -> Unit,
    onActivityDurationChange: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Target Durasi", fontSize = 14.sp, fontWeight = FontWeight.W600)
            Switch(
                checked = isDurationEnabled,
                onCheckedChange = onDurationEnabledChange
            )
        }
        AnimatedVisibility(visible = isDurationEnabled) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                ActionField(
                    text = activityDuration,
                    trailingIcon = Icons.Default.Timer,
                    onClick = { onShowDurationPickerChange(true) }
                )
            }
        }
    }
}