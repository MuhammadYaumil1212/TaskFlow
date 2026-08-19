package yr.muhammadyaumil.taskflow.presentations.ui.signIn

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.R
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.components.LoginButtonWithSocialMedia
import yr.muhammadyaumil.taskflow.presentations.ui.signIn.components.SignInTextField

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onForgotPassClick: (offset: Offset) -> Unit
) {
    Scaffold(modifier = modifier) { innerPadding ->
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
                        onSignInClick = { },
                        onSignUpClick = onSignUpClick,
                        onForgotPassClick = onForgotPassClick
                    )
                }
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
fun LoginForm(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPassClick: (offset: Offset) -> Unit
) {
    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
        SignInTextField(
            modifier = Modifier,
            text = "Username",
            hint = "Masukkan Username",
            valueText = usernameText,
            onValueChanged = { usernameText = it })
        Spacer(modifier = Modifier.height(15.dp))
        SignInTextField(
            modifier = Modifier,
            text = "Password",
            "Masukkan Password Anda",
            isPassword = true,
            valueText = passwordText,
            onValueChanged = { passwordText = it })
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
            MaterialTheme
                .colorScheme
                .primary
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            stringResource(
                R.string.btn_login_text
            )
        )
    }
    OutlinedButton(
        onClick = onSignUpClick,
        shape = RoundedCornerShape(size = 10.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            stringResource(R.string.sign_up_text),
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            Modifier.width(110.dp),
            DividerDefaults.Thickness,
            DividerDefaults.color,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Atau Masuk Dengan", fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(5.dp))
        HorizontalDivider(Modifier.width(110.dp), DividerDefaults.Thickness, DividerDefaults.color)
    }
    Spacer(modifier = Modifier.height(25.dp))
    LoginButtonWithSocialMedia(
        modifier = Modifier,
        icon = painterResource(R.drawable.ic_google),
        text = "Google",
        onClick = {},
    )
    Spacer(modifier = Modifier.height(15.dp))
    LoginButtonWithSocialMedia(
        modifier = Modifier,
        icon = painterResource(R.drawable.ic_facebook),
        text = "Facebook",
        onClick = {},
    )

}