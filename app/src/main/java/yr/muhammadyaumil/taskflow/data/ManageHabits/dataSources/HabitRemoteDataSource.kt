package yr.muhammadyaumil.taskflow.data.ManageHabits.dataSources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

interface HabitRemoteDataSource {
    suspend fun addHabit(habit: HabitDto)
    suspend fun getHabit(): List<HabitDto>
}

class HabitRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HabitRemoteDataSource {

    override suspend fun addHabit(habit: HabitDto) {
        val userId = auth.currentUser?.uid ?: throw Exception("User Belum Login")

        val documentRef = firestore
            .collection("users")
            .document(userId)
            .collection("habits")
            .document()

        val habitWithId = habit.copy(id = documentRef.id)
        documentRef.set(habitWithId).await()
    }

    override suspend fun getHabit(): List<HabitDto> {
        val userId = auth.currentUser?.uid ?: throw Exception("User Belum Login")

        val querySnapshot = firestore
            .collection("users")
            .document(userId)
            .collection("habits")
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { document ->
            document.toObject(HabitDto::class.java)
        }
    }
}