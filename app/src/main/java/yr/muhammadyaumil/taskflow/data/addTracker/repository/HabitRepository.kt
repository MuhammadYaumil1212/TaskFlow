package yr.muhammadyaumil.taskflow.data.addTracker.repository

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import jakarta.inject.Inject
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.addTracker.dataSources.HabitRemoteDataSource
import yr.muhammadyaumil.taskflow.data.addTracker.models.HabitDto
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface HabitRepository {
    suspend fun createNewHabit(
        name: String,
        notes: String,
        isDurationEnabled: Boolean,
        duration: String,
        frequency: String,
        reminder: String,
        isAttachmentEnabled: Boolean,
        categoryHex: String
    ): Response<Unit>
}

class HabitRepositoryImpl @Inject constructor(
    private val remoteDataSource: HabitRemoteDataSource
) : HabitRepository {
    override suspend fun createNewHabit(
        name: String,
        notes: String,
        isDurationEnabled: Boolean,
        duration: String,
        frequency: String,
        reminder: String,
        isAttachmentEnabled: Boolean,
        categoryHex: String
    ): Response<Unit> {
        return try {
            val newHabit = HabitDto(
                name = name,
                notes = notes,
                duration = duration,
                frequency = frequency,
                reminder = reminder,
                categoryHex = categoryHex
            )
            remoteDataSource.addHabit(newHabit)
            Response.Success(Unit)
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

}