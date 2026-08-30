package yr.muhammadyaumil.taskflow.presentations.ui.signUp

sealed interface SignUpState {
    data object Loading : SignUpState
    data object SuccessRegister : SignUpState
    data object Failed : SignUpState
}