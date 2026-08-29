package yr.muhammadyaumil.taskflow.presentations.ui.Tracker

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import yr.muhammadyaumil.taskflow.core.components.LoadingSpinner
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.signIn.models.LogoutResult

@Composable
fun TrackerScreen(
    authState: Response<LogoutResult>?,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authState) {
        if (authState is Response.Error) {
            snackbarHostState.showSnackbar(
                message = authState.message,
                duration = SnackbarDuration.Short
            )
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Text("Tracker Screen")
                ElevatedButton(
                    onClick = onLogout
                ) { Text("Logout", fontWeight = FontWeight.Bold, fontSize = 14.sp) }
            }
        }
        if (authState is Response.Loading) {
            LoadingSpinner()
        }
    }
}