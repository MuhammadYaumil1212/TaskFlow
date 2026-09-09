package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.Category
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.DocumentUpload
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.DurationPickerDialog
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.DurationTarget
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.FrequencyAndReminder
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.Header
import yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components.TitleAndNotes
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrackerScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    habitName: String,
    habitNotes: String,
    isDurationEnabled: Boolean,
    activityDuration: String,
    isAttachmentEnabled: Boolean,
    frequency: String,
    reminder: String,
    isHabitNameError: Boolean,
    isHabitNotesError: Boolean,
    errorMessage: String?,
    onClearError: () -> Unit,
    selectedCategoryHex: String,
    frequencyExpanded: Boolean,
    showTimePicker: Boolean,
    showDurationPicker: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onHabitNameChange: (String) -> Unit,
    onHabitNotesChange: (String) -> Unit,
    onDurationEnabledChange: (Boolean) -> Unit,
    onActivityDurationChange: (String) -> Unit,
    onAttachmentEnabledChange: (Boolean) -> Unit,
    onFrequencyChange: (String) -> Unit,
    onReminderChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onFrequencyExpandedChange: (Boolean) -> Unit,
    onShowTimePickerChange: (Boolean) -> Unit,
    onShowDurationPickerChange: (Boolean) -> Unit,
) {
    val categories = remember {
        mutableStateListOf(
            "KESEHATAN" to "#9DB499",
            "PIKIRAN" to "#E8C7AC",
            "FOKUS" to "#B5D2E8",
            "FISIK" to "#D9C6E8"
        )
    }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    val frequencyOptions = listOf("Setiap Hari", "Hari Kerja", "Akhir Pekan", "Setiap Minggu")

    val calendar = remember { Calendar.getInstance() }

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
        is24Hour = true
    )

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message = message)
            onClearError()
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item {
                Header(
                    onBack = onBack,
                    onSave = onSave
                )
            }

            item {
                TitleAndNotes(
                    onHabitNameChange = onHabitNameChange,
                    onHabitNotesChange = onHabitNotesChange,
                    habitName = habitName,
                    habitNotes = habitNotes,
                    isHabitNameError = isHabitNameError,
                    isHabitNotesError = isHabitNotesError
                )
            }

            item {
                DurationTarget(
                    isDurationEnabled = isDurationEnabled,
                    onDurationEnabledChange = onDurationEnabledChange,
                    activityDuration = activityDuration,
                    onActivityDurationChange = onActivityDurationChange,
                    onShowDurationPickerChange = onShowDurationPickerChange,
                )
            }

            item {
                DocumentUpload(
                    isAttachmentEnabled = isAttachmentEnabled,
                    onAttachmentEnabledChange = onAttachmentEnabledChange
                )
            }

            item {
                FrequencyAndReminder(
                    frequency = frequency,
                    onFrequencyChange = onFrequencyChange,
                    frequencyExpanded = frequencyExpanded,
                    frequencyOptions = frequencyOptions,
                    onFrequencyExpandedChange = onFrequencyExpandedChange,
                    reminder = reminder,
                    onShowTimePickerChange = onShowTimePickerChange
                )
            }

            item {
                Category(
                    categories = categories,
                    selectedCategoryHex = selectedCategoryHex,
                    onCategoryChange = onCategoryChange,
                    onAddCategoryClick = { showAddCategoryDialog = true }
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
    if (isLoading) {
        LoadingSpinner()
    }
    if (showAddCategoryDialog) {
        val allColors = listOf(
            "#9DB499", "#E8C7AC", "#B5D2E8", "#D9C6E8",
            "#F4A261", "#E76F51", "#2A9D8F", "#E9C46A",
            "#264653", "#A8DADC", "#457B9D", "#1D3557",
            "#D4A373", "#CCD5AE", "#E9EDC9", "#FEFAE0"
        )

        val usedColors = categories.map { it.second }
        val availableColors = allColors.filterNot { it in usedColors }

        var newCategoryName by remember { mutableStateOf("") }
        var newCategoryColor by remember {
            mutableStateOf(availableColors.firstOrNull() ?: "#000000")
        }

        AlertDialog(
            onDismissRequest = { showAddCategoryDialog = false },
            title = { Text("Kategori Baru", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = newCategoryName,
                        onValueChange = { newCategoryName = it },
                        label = { Text("Nama Kategori") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (availableColors.isEmpty()) {
                        Text(
                            text = "Semua warna telah digunakan",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text("Pilih Warna", fontSize = 14.sp, fontWeight = FontWeight.W600)
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            availableColors.chunked(4).forEach { rowColors ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    rowColors.forEach { hex ->
                                        val colorInt = hex.toColorInt()
                                        Box(
                                            modifier = Modifier
                                                .padding(end = 16.dp)
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(Color(colorInt))
                                                .border(
                                                    width = if (newCategoryColor == hex) 3.dp else 0.dp,
                                                    color = if (newCategoryColor == hex) MaterialTheme.colorScheme.primary else Color.Transparent,
                                                    shape = CircleShape
                                                )
                                                .clickable { newCategoryColor = hex }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newCategoryName.isNotBlank() && availableColors.isNotEmpty()) {
                            categories.add(newCategoryName.uppercase() to newCategoryColor)
                            onCategoryChange(newCategoryColor)
                            showAddCategoryDialog = false
                        }
                    },
                    enabled = newCategoryName.isNotBlank() && availableColors.isNotEmpty()
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCategoryDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    if (showDurationPicker) {
        DurationPickerDialog(
            onDismissRequest = { onShowDurationPickerChange(false) },
            onSave = { formattedDuration ->
                onActivityDurationChange(formattedDuration)
                onShowDurationPickerChange(false)
            }
        )
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { onShowTimePickerChange(false) },
            confirmButton = {
                TextButton(onClick = {
                    val formattedTime = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        timePickerState.hour,
                        timePickerState.minute
                    )
                    onReminderChange(formattedTime)
                    onShowTimePickerChange(false)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onShowTimePickerChange(false) }) {
                    Text("Batal")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}