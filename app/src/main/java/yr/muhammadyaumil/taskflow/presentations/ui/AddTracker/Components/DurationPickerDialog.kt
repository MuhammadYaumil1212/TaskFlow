package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DurationPickerDialog(
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit
) {
    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Atur Target Durasi") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Kolom Jam
                OutlinedTextField(
                    value = hours,
                    onValueChange = {
                        if (it.length <= 2) hours = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Jam") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // Kolom Menit
                OutlinedTextField(
                    value = minutes,
                    onValueChange = {
                        if (it.length <= 2) minutes = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Menit") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // Kolom Detik
                OutlinedTextField(
                    value = seconds,
                    onValueChange = {
                        if (it.length <= 2) seconds = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("Detik") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val h = hours.toIntOrNull() ?: 0
                    val m = minutes.toIntOrNull() ?: 0
                    val s = seconds.toIntOrNull() ?: 0

                    val result = mutableListOf<String>()
                    if (h > 0) result.add("$h jam")
                    if (m > 0) result.add("$m menit")
                    if (s > 0) result.add("$s detik")

                    val finalDuration =
                        if (result.isEmpty()) "Tidak ada" else result.joinToString(" ")
                    onSave(finalDuration)
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Batal")
            }
        }
    )
}