package yr.muhammadyaumil.taskflow.data.signIn.Repository

import android.util.Log
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.signIn.DataSources.AuthRemote
import yr.muhammadyaumil.taskflow.data.signIn.models.AuthResult
import yr.muhammadyaumil.taskflow.data.signIn.models.LogoutResult
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface SignInRepository {
    suspend fun signInWithGoogle(): Response<AuthResult>

    fun isLoggedIn(): Boolean
    suspend fun logout(): Response<LogoutResult>
}

class SignInRepositoryImpl @Inject constructor(private val authRemote: AuthRemote) :
    SignInRepository {
    override suspend fun signInWithGoogle(): Response<AuthResult> {
        return try {
            val dataResult = authRemote.signInWithGoogle()
            return Response.Success(dataResult)
        } catch (e: Exception) {
            Log.e("ERROR SIGN IN", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        }
    }

    override fun isLoggedIn(): Boolean = authRemote.isLoggedIn()

    override suspend fun logout(): Response<LogoutResult> {
        return try {
            val logoutResult = authRemote.signOut()
            return Response.Success(logoutResult)
        } catch (e: Exception) {
            Log.e("ERROR SIGN IN", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: IOException) {
            Log.e("NETWORK ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: SocketTimeoutException) {
            Log.e("SOCKET ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        } catch (e: UnknownHostException) {
            Log.e("CONNECTION ERROR ", e.message ?: "Something went wrong")
            Response.Error(e.message ?: "Something went wrong")
        }
    }
}