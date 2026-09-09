package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.R
import yr.muhammadyaumil.taskflow.presentations.components.AppTextField
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.components.LoginButtonWithSocialMedia

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    usernameText: String,
    emailText: String,
    passwordText: String,
    confirmPasswordText: String,
    isLoading: Boolean,
    errorMessage: String?,
    isUsernameError: Boolean,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isConfirmPasswordError: Boolean,
    emailValueChanged: (String) -> Unit,
    usernameValueChanged: (String) -> Unit,
    passwordValueChanged: (String) -> Unit,
    confirmPasswordValueChanged: (String) -> Unit,
    onTapSignUp: () -> Unit,
    onClearError: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            onClearError()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .systemBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    HeaderLogo()
                    Spacer(modifier = Modifier.height(20.dp))
                    RegisterForm(
                        usernameText = usernameText,
                        passwordText = passwordText,
                        emailText = emailText,
                        confirmPasswordText = confirmPasswordText,
                        isUsernameError = isUsernameError,
                        isEmailError = isEmailError,
                        isPasswordError = isPasswordError,
                        isConfirmPasswordError = isConfirmPasswordError,
                        emailValueChanged = emailValueChanged,
                        usernameValueChanged = usernameValueChanged,
                        passwordValueChanged = passwordValueChanged,
                        confirmPasswordValueChanged = confirmPasswordValueChanged,
                        oneTapGoogleLogin = {},
                        oneTapFacebookLogin = {},
                        onSignUpClick = onTapSignUp,
                    )
                }
            }
            if (isLoading) {
                LoadingSpinner()
            }
        }
    }
}

@Composable
fun HeaderLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Masuk ke Dalam Akun mu",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

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

@Composable
fun DividerOrRegisterWith() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            Modifier.width(110.dp),
            DividerDefaults.Thickness,
            DividerDefaults.color,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Atau Daftar Dengan", fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(5.dp))
        HorizontalDivider(Modifier.width(110.dp), DividerDefaults.Thickness, DividerDefaults.color)
    }
}

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