package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FrequencyAndReminder(
    modifier: Modifier = Modifier,
    frequency: String,
    onFrequencyExpandedChange: (Boolean) -> Unit,
    frequencyExpanded: Boolean,
    frequencyOptions: List<String>,
    onFrequencyChange: (String) -> Unit,
    reminder: String,
    onShowTimePickerChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Frekuensi", fontSize = 14.sp, fontWeight = FontWeight.W600)
            Spacer(modifier = Modifier.height(8.dp))
            Box {
                ActionField(
                    text = frequency,
                    onClick = { onFrequencyExpandedChange(true) }
                )
                DropdownMenu(
                    expanded = frequencyExpanded,
                    onDismissRequest = { onFrequencyExpandedChange(false) }
                ) {
                    frequencyOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onFrequencyChange(option)
                                onFrequencyExpandedChange(false)
                            }
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Pengingat", fontSize = 14.sp, fontWeight = FontWeight.W600)
            Spacer(modifier = Modifier.height(8.dp))
            ActionField(
                text = reminder,
                trailingIcon = Icons.Default.Notifications,
                onClick = { onShowTimePickerChange(true) }
            )
        }
    }
}