package yr.muhammadyaumil.taskflow.data.authentication.repository

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "User Cancelled")
            Response.Error("Proses login dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
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
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "User Cancelled")
            Response.Error("Proses login dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override fun isLoggedIn(): Boolean = authRemote.isLoggedIn()
    override suspend fun getUserDisplayName(): Response<AuthResult> {
        return try {
            val getDisplay = authRemote.getUserDisplayName()
            Response.Success(getDisplay)
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "User Cancelled")
            Response.Error("Proses login dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override suspend fun logout(): Response<LogoutResult> {
        return try {
            val logoutResult = authRemote.signOut()
            return Response.Success(logoutResult)
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "User Cancelled")
            Response.Error("Proses login dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
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
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: GetCredentialCancellationException) {
            Log.e("CANCELLATION ERROR", e.localizedMessage ?: "User Cancelled")
            Response.Error("Proses login dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }
}