package yr.muhammadyaumil.taskflow.data.authentication.dataSources

import android.content.Context
import android.content.MutableContextWrapper
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import yr.muhammadyaumil.taskflow.data.authentication.models.AuthResult
import yr.muhammadyaumil.taskflow.data.authentication.models.LogoutResult
import yr.muhammadyaumil.taskflow.data.authentication.models.UserData
import javax.inject.Inject

class AuthRemote @Inject constructor(@ApplicationContext private val context: Context) {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val credentialManager: CredentialManager by lazy { CredentialManager.create(context) }
    private val getGoogleIdOption: GetGoogleIdOption by lazy {
        GetGoogleIdOption.Builder()
            .setServerClientId("126990699104-mq7ersc307ivn4hlv1qpc6i5s7kd9kvn.apps.googleusercontent.com")
            .setFilterByAuthorizedAccounts(false)
            .build()
    }

    private val firestoreDb: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val getCredentialRequest: GetCredentialRequest by lazy {
        GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()
    }

    suspend fun signInWithGoogle(): AuthResult {
        val contextWrapper = MutableContextWrapper(context)
        val credential =
            credentialManager.getCredential(
                contextWrapper,
                getCredentialRequest
            ).credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken // jwt token
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

            firebaseAuth.signInWithCredential(firebaseCredential).await()
            googleIdTokenCredential.apply {
                val userData = UserData(
                    username = displayName,
                    email = email,
                    profilePicture = profilePictureUri
                )
                val authResult = AuthResult(userData = userData)
                return authResult
            }
        } else {
            return AuthResult(errorMessage = "Invalid credential")
        }
    }

    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    suspend fun signOut(): LogoutResult {
        firebaseAuth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        return LogoutResult(successLogout = "Sucessfully Logout")
    }

    suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String,
    ): AuthResult {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        val user = authResult.user ?: throw Exception("Gagal membuat user, data null")

        user.sendEmailVerification().await()

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        user.updateProfile(profileUpdates).await()

        val saveUser = UserData(
            username = username,
            email = user.email ?: "",
            profilePicture = user.photoUrl,
            confirmationStatus = user.isEmailVerified
        )

        firestoreDb.collection("users")
            .document(user.uid)
            .set(saveUser)
            .await()

        return AuthResult(successRegistration = true)
    }
}