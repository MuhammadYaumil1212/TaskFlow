package yr.muhammadyaumil.taskflow.presentations.ui.AddTracker.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.presentations.components.AppTextField

@Composable
fun TitleAndNotes(
    modifier: Modifier = Modifier,
    habitName: String,
    isHabitNameError: Boolean,
    isHabitNotesError: Boolean,
    onHabitNameChange: (String) -> Unit,
    habitNotes: String,
    onHabitNotesChange: (String) -> Unit
) {
    Column {
        Text(text = "Nama Kegiatan", fontSize = 14.sp, fontWeight = FontWeight.W600)
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChanged = onHabitNameChange,
            hint = "Contoh: Mandi Pagi, Makan Siang...",
            isError = isHabitNameError,
            valueText = habitName
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Catatan", fontSize = 14.sp, fontWeight = FontWeight.W600)
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChanged = onHabitNotesChange,
            minLines = 4,
            maxLines = 6,
            singleLine = false,
            hint = "Contoh: Mandi setelah lari pagi",
            isError = isHabitNotesError,
            valueText = habitNotes
        )
    }
}