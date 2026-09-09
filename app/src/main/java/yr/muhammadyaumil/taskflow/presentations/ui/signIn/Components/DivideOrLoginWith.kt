package yr.muhammadyaumil.taskflow.presentations.ui.signIn.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DividerOrLoginWith(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            Modifier.width(110.dp),
            DividerDefaults.Thickness,
            DividerDefaults.color,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Atau Masuk Dengan", fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(5.dp))
        HorizontalDivider(Modifier.width(110.dp), DividerDefaults.Thickness, DividerDefaults.color)
    }
}