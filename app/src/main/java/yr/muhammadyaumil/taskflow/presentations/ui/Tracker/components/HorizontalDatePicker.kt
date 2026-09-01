package yr.muhammadyaumil.taskflow.presentations.ui.Tracker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle

@Composable
fun HorizontalDatePicker(
    modifier: Modifier = Modifier,
    daysCount: Int = 30,
    onDateSelected: (LocalDate) -> Unit
) {

    val todayDate = LocalDate.now()
    val listState = rememberLazyListState()
    var selectedDate by remember { mutableStateOf(todayDate) }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(daysCount) { index ->
            val date = todayDate.plusDays(index.toLong())
            val isSelected = date == selectedDate
            Surface(
                selected = isSelected,
                onClick = {
                    selectedDate = date
                    onDateSelected(date)
                },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .size(60.dp, 80.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.dayOfWeek.getDisplayName(
                            TextStyle.SHORT,
                            LocalLocale.current.platformLocale
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }

}