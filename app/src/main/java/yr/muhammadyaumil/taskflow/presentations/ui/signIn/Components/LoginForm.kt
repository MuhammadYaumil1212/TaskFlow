package yr.muhammadyaumil.taskflow.presentations.ui.signIn.Components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.R
import yr.muhammadyaumil.taskflow.presentations.components.AppTextField

@Composable
fun LoginForm(
    usernameText: String,
    passwordText: String,
    isUsernameError: Boolean,
    isPasswordError: Boolean,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPassClick: (offset: Offset) -> Unit,
    onTapGoogleLogin: () -> Unit,
    onTapFacebookLogin: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Masukkan Username",
            isError = isUsernameError,
            valueText = usernameText,
            onValueChanged = onUsernameChanged
        )

        Spacer(modifier = Modifier.height(15.dp))

        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Masukkan Password Anda",
            isPassword = true,
            isError = isPasswordError,
            valueText = passwordText,
            onValueChanged = onPasswordChanged
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = onForgotPassClick)
                },
            text = "Lupa Password ? ",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    ElevatedButton(
        onClick = onSignInClick,
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(stringResource(R.string.btn_login_text))
    }
    OutlinedButton(
        onClick = onSignUpClick,
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(stringResource(R.string.sign_up_text), color = Color.Black)
    }
    Spacer(modifier = Modifier.height(20.dp))
    DividerOrLoginWith()
    Spacer(modifier = Modifier.height(25.dp))
    OneTapLogin(
        onTapGoogleLogin = onTapGoogleLogin,
        onTapFacebookLogin = onTapFacebookLogin
    )
}