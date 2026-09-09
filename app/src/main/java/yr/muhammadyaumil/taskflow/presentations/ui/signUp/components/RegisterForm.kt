package yr.muhammadyaumil.taskflow.presentations.ui.signUp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.R
import yr.muhammadyaumil.taskflow.presentations.components.AppTextField

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    usernameText: String,
    emailText: String,
    passwordText: String,
    confirmPasswordText: String,
    isUsernameError: Boolean,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isConfirmPasswordError: Boolean,
    emailValueChanged: (String) -> Unit,
    usernameValueChanged: (String) -> Unit,
    passwordValueChanged: (String) -> Unit,
    confirmPasswordValueChanged: (String) -> Unit,
    oneTapGoogleLogin: () -> Unit,
    oneTapFacebookLogin: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Masukkan Email",
            valueText = emailText,
            isError = isEmailError,
            onValueChanged = emailValueChanged
        )
        Spacer(modifier = Modifier.height(15.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Masukkan Username",
            isError = isUsernameError,
            valueText = usernameText,
            onValueChanged = usernameValueChanged
        )
        Spacer(modifier = Modifier.height(15.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Masukkan Password Anda",
            isPassword = true,
            valueText = passwordText,
            isError = isPasswordError,
            onValueChanged = passwordValueChanged
        )
        Spacer(modifier = Modifier.height(15.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = "Konfirmasi Password Anda",
            isPassword = true,
            valueText = confirmPasswordText,
            isError = isConfirmPasswordError,
            onValueChanged = confirmPasswordValueChanged
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    ElevatedButton(
        onClick = onSignUpClick,
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(stringResource(R.string.sign_up_btn_text))
    }
    Spacer(modifier = Modifier.height(20.dp))
    DividerOrRegisterWith()
    Spacer(modifier = Modifier.height(25.dp))
    OneTapLogin(
        onTapGoogleLogin = oneTapGoogleLogin,
        onTapFacebookLogin = oneTapFacebookLogin,
    )
}