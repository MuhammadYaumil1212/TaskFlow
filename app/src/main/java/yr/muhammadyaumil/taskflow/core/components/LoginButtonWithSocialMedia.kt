package yr.muhammadyaumil.taskflow.presentations.ui.signIn.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.R

@Composable
fun LoginButtonWithSocialMedia(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
        )
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            tint = Color.Unspecified,
            contentDescription = "$text icon",
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text)
    }
}

@Preview
@Composable
fun PreviewLoginButton(modifier: Modifier = Modifier) {
    LoginButtonWithSocialMedia(
        modifier = modifier,
        icon = painterResource(R.drawable.ic_google),
        text = "Dengan Google",
        onClick = {},
    )
}