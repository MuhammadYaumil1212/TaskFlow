package yr.muhammadyaumil.taskflow.presentations.ui.signIn.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.R
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.components.LoginButtonWithSocialMedia

@Composable
fun OneTapLogin(
    modifier: Modifier = Modifier,
    onTapGoogleLogin: () -> Unit,
    onTapFacebookLogin: () -> Unit
) {
    Column(modifier = modifier) {
        LoginButtonWithSocialMedia(
            modifier = Modifier,
            icon = painterResource(R.drawable.ic_google),
            text = "Google",
            onClick = onTapGoogleLogin,
        )
        Spacer(modifier = Modifier.height(15.dp))
        LoginButtonWithSocialMedia(
            modifier = Modifier,
            icon = painterResource(R.drawable.ic_facebook),
            text = "Facebook",
            onClick = onTapFacebookLogin,
        )
    }
}