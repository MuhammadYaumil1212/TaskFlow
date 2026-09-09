package yr.muhammadyaumil.taskflow.data.addTracker.dataSources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import yr.muhammadyaumil.taskflow.core.response.Response
import yr.muhammadyaumil.taskflow.data.addTracker.models.HabitDto


interface HabitRemoteDataSource {
    suspend fun addHabit(habit: HabitDto): Response<Unit>
}

class HabitRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HabitRemoteDataSource {
    override suspend fun addHabit(habit: HabitDto): Response<Unit> {
        val userId = auth.currentUser?.uid ?: throw Exception("User Belum Login")
        val documentRef = firestore
            .collection("users")
            .document(userId)
            .collection("habits")
            .document()

        val habitWithId = habit.copy(id = documentRef.id)
        documentRef.set(habitWithId).await()
        return Response.Success(Unit)
    }

}