package yr.muhammadyaumil.taskflow.presentations.ui.detailhabits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

@Composable
fun PlayAndDelete(
    modifier: Modifier = Modifier,
    habit: HabitDto?,
    onPlay: () -> Unit,
    onDelete: () -> Unit,

    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                habit?.name ?: "Unknown",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                habit?.frequency ?: "Unknown",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary),
            onClick = onPlay
        ) {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                tint = Color.White,
                contentDescription = "Play arrow icons",
                modifier = Modifier.size(25.dp)
            )
        }
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                tint = Color.Red,
                contentDescription = "Delete Icons",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}