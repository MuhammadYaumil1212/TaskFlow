package yr.muhammadyaumil.taskflow.data.ManageHabits.repository

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.ManageHabits.dataSources.HabitRemoteDataSource
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.CategoryDto
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface HabitRepository {
    suspend fun createNewHabit(habit: HabitDto): Response<Unit>
    suspend fun addCategory(habit: CategoryDto): Response<Unit>
    suspend fun getCategoryHabit(): Flow<Response<List<CategoryDto>>>
    suspend fun getDetailHabit(docsId: String): Response<HabitDto?>
    fun getHabit(): Flow<Response<List<HabitDto>>>
}

class HabitRepositoryImpl @Inject constructor(
    private val remoteDataSource: HabitRemoteDataSource
) : HabitRepository {
    override suspend fun createNewHabit(
        habit: HabitDto
    ): Response<Unit> {
        return try {
            remoteDataSource.addHabit(habit)
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
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override suspend fun addCategory(habit: CategoryDto): Response<Unit> {
        return try {
            remoteDataSource.addCategory(habit)
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
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

    override suspend fun getCategoryHabit(): Flow<Response<List<CategoryDto>>> =
        flow<Response<List<CategoryDto>>> {
            val getCategoryHabits = remoteDataSource.getCategoryHabit()
            emit(Response.Success(getCategoryHabits))
        }.onStart {
            emit(Response.Loading)
        }.catch { e ->
            val errorTag = when (e) {
                is SocketTimeoutException -> "SOCKET ERROR"
                is UnknownHostException -> "CONNECTION ERROR"
                is IOException -> "NETWORK ERROR"
                is GetCredentialCancellationException -> "CANCELLATION ERROR"
                else -> "GENERAL ERROR"
            }
            Log.e(errorTag, e.localizedMessage ?: "Something went wrong")
            emit(Response.Error(e.localizedMessage ?: "Something went wrong"))
        }

    override fun getHabit(): Flow<Response<List<HabitDto>>> = flow<Response<List<HabitDto>>> {
        val getHabit = remoteDataSource.getHabit()
        emit(Response.Success(getHabit))
    }.onStart {
        emit(Response.Loading)
    }.catch { e ->
        val errorTag = when (e) {
            is SocketTimeoutException -> "SOCKET ERROR"
            is UnknownHostException -> "CONNECTION ERROR"
            is IOException -> "NETWORK ERROR"
            is GetCredentialCancellationException -> "CANCELLATION ERROR"
            else -> "GENERAL ERROR"
        }
        Log.e(errorTag, e.localizedMessage ?: "Something went wrong")
        emit(Response.Error(e.localizedMessage ?: "Something went wrong"))
    }

    override suspend fun getDetailHabit(docsId: String): Response<HabitDto?> {
        return try {
            val getHabitById = remoteDataSource.getHabitById(docsId)
            Response.Success(getHabitById)
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR", e.localizedMessage ?: "Timeout")
            Response.Error("Koneksi internet sangat lambat. Silakan coba beberapa saat lagi.")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR", e.localizedMessage ?: "No Internet")
            Response.Error("Tidak ada koneksi internet. Periksa jaringan Anda dan coba lagi.")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR", e.localizedMessage ?: "Network Issue")
            Response.Error("Terjadi gangguan jaringan. Pastikan koneksi internet stabil.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AUTH ERROR", e.localizedMessage ?: "Invalid Credentials")
            Response.Error("Email atau password yang dimasukkan salah.")
        } catch (e: Exception) {
            Log.e("GENERAL ERROR", e.localizedMessage ?: "Unknown Error")
            Response.Error("Terjadi kesalahan sistem. Silakan coba lagi nanti.")
        }
    }

}