package yr.muhammadyaumil.taskflow.data.authentication.repository

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.dataSources.AuthRemote
import yr.muhammadyaumil.taskflow.data.authentication.models.AuthResult
import yr.muhammadyaumil.taskflow.data.authentication.models.LogoutResult
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface AuthenticationRepository {
    // for sign in
    suspend fun signInWithGoogle(): Response<AuthResult>
    suspend fun signInWithUsernameAndPassword(
        username: String,
        password: String
    ): Response<AuthResult>

    fun isLoggedIn(): Boolean
    suspend fun getUserDisplayName(): Response<AuthResult>
    suspend fun logout(): Response<LogoutResult>

    //for sign up
    suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String,
    ): Response<AuthResult>

}

class AuthenticationRepositoryImpl @Inject constructor(private val authRemote: AuthRemote) :
    AuthenticationRepository {
    override suspend fun signInWithGoogle(): Response<AuthResult> {
        return try {
            val dataResult = authRemote.signInWithGoogle()
            return Response.Success(dataResult)
        } catch (e: Exception) {
            Log.e("ERROR SIGN IN", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun signInWithUsernameAndPassword(
        username: String,
        password: String
    ): Response<AuthResult> {
        return try {
            val result = authRemote.signInWithUsernameAndPassword(username, password)
            if (!result.errorMessage.isNullOrEmpty()) {
                Response.Error(result.errorMessage)
            } else {
                Response.Success(result)
            }
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e("ERROR COLLISION USER", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    override fun isLoggedIn(): Boolean = authRemote.isLoggedIn()
    override suspend fun getUserDisplayName(): Response<AuthResult> {
        return try {
            val getDisplay = authRemote.getUserDisplayName()
            Response.Success(getDisplay)
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun logout(): Response<LogoutResult> {
        return try {
            val logoutResult = authRemote.signOut()
            return Response.Success(logoutResult)
        } catch (e: Exception) {
            Log.e("ERROR SIGN IN", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String,
    ): Response<AuthResult> {
        return try {
            val regisUser = authRemote.signUpWithEmailAndPassword(
                email = email,
                username = username,
                password = password,
            )
            Response.Success(regisUser)
        } catch (e: Exception) {
            Log.e("ERROR SIGN IN", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.e("ERROR COLLISION USER", e.localizedMessage ?: "Something went wrong")
            Response.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
}