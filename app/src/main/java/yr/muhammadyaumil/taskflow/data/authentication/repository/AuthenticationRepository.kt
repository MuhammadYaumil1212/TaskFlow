package yr.muhammadyaumil.taskflow.data.authentication.repository

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.authentication.dataSources.AuthRemote
import yr.muhammadyaumil.taskflow.data.authentication.models.UserData
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface AuthenticationRepository {
    suspend fun signInWithGoogle(): Response<Unit>

    suspend fun signInWithUsernameAndPassword(
        username: String,
        password: String
    ): Response<Unit>

    fun isLoggedIn(): Boolean

    fun getUserData(): Response<UserData>

    suspend fun logout(): Response<Unit>

    suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String,
    ): Response<Unit>
}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authRemote: AuthRemote
) : AuthenticationRepository {

    override suspend fun signInWithGoogle(): Response<Unit> {
        return try {
            authRemote.signInWithGoogle()
            Response.Success(Unit)
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
            // Menggunakan e.message agar pesan error spesifik dari Remote Data Source dapat diteruskan ke UI
            Response.Error(e.message ?: "Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override suspend fun signInWithUsernameAndPassword(
        username: String,
        password: String
    ): Response<Unit> {
        return try {
            authRemote.signInWithUsernameAndPassword(username, password)
            Response.Success(Unit)
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
            Response.Error(e.message ?: "Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override fun isLoggedIn(): Boolean = authRemote.isLoggedIn()

    override fun getUserData(): Response<UserData> {
        return try {
            val userData = authRemote.getUserData()
            Response.Success(userData)
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error(e.message ?: "Gagal mengambil data user.")
        }
    }

    override suspend fun logout(): Response<Unit> {
        return try {
            authRemote.signOut()
            Response.Success(Unit)
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error(e.message ?: "Terjadi kesalahan sistem saat proses logout.")
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        username: String,
        password: String,
    ): Response<Unit> {
        return try {
            authRemote.signUpWithEmailAndPassword(
                email = email,
                username = username,
                password = password,
            )
            Response.Success(Unit)
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
            Response.Error("Proses registrasi dibatalkan.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Format email tidak valid atau sudah digunakan.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error(e.message ?: "Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }
}