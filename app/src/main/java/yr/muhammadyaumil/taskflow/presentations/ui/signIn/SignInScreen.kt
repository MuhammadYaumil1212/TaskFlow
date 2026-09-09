package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import yr.muhammadyaumil.taskflow.presentations.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.Components.HeaderLogo
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.Components.LoginForm

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    usernameText: String,
    passwordText: String,
    isLoading: Boolean,
    errorMessage: String?,
    isUsernameError: Boolean,
    isPasswordError: Boolean,
    usernameValueChanged: (String) -> Unit,
    passwordValueChanged: (String) -> Unit,
    onClearError: () -> Unit,
    onSignUpClick: () -> Unit,
    onTapGoogleLogin: () -> Unit,
    onSignInClick: () -> Unit,
    onForgotPassClick: (offset: Offset) -> Unit
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
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
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
                    LoginForm(
                        usernameText = usernameText,
                        passwordText = passwordText,
                        isUsernameError = isUsernameError,
                        isPasswordError = isPasswordError,
                        onUsernameChanged = usernameValueChanged,
                        onPasswordChanged = passwordValueChanged,
                        onSignInClick = onSignInClick,
                        onSignUpClick = onSignUpClick,
                        onForgotPassClick = onForgotPassClick,
                        onTapFacebookLogin = {},
                        onTapGoogleLogin = onTapGoogleLogin
                    )
                }
            }
        }

        if (isLoading) {
            LoadingSpinner()
        }
    }
}