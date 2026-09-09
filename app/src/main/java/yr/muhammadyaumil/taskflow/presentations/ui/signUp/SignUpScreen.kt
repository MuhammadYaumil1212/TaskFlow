package yr.muhammadyaumil.taskflow.presentations.ui.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.components.HeaderLogo
import yr.muhammadyaumil.taskflow.presentations.ui.signUp.components.RegisterForm

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