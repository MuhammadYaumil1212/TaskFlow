package yr.muhammadyaumil.taskflow.data.authentication.models

data class AuthResult(
    val userData: UserData? = null,
    val errorMessage: String? = null,
    val successRegistration: Boolean? = false,
    val successLogin: Boolean? = false
)
