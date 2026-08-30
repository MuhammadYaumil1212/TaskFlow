package yr.muhammadyaumil.taskflow.data.authentication.models

import android.net.Uri

data class UserData(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val profilePicture: Uri? = null,
    val confirmationStatus: Boolean? = false
)
