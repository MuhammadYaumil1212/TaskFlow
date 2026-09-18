package yr.muhammadyaumil.taskflow.data.ManageHabits.dataSources

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.CategoryDto
import yr.muhammadyaumil.taskflow.data.ManageHabits.models.HabitDto

interface HabitRemoteDataSource {
    suspend fun addHabit(habit: HabitDto)
    suspend fun addCategory(category: CategoryDto)
    suspend fun getCategoryHabit(): List<CategoryDto>
    suspend fun getHabit(): List<HabitDto>

    suspend fun getHabitById(docsId: String): HabitDto?
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

    override suspend fun addCategory(category: CategoryDto) {
        val userId = auth.currentUser?.uid
            ?: throw Exception("User Belum Login")

        val documentRef = firestore
            .collection("users")
            .document(userId)

        val snapshot = documentRef.get().await()

        val categories = (snapshot.get("categories") as? Map<*, *>)
            ?.mapNotNull { (key, value) ->
                if (key is String && value is String) {
                    key to value
                } else {
                    null
                }
            }
            ?.toMap()
            ?.toMutableMap()
            ?: mutableMapOf()

        categories[category.name] = category.color

        documentRef.update(
            "categories",
            categories
        ).await()
    }

    override suspend fun getCategoryHabit(): List<CategoryDto> {
        val user = FirebaseAuth.getInstance().currentUser

        return if (user != null) {
            val snapshot = firestore
                .collection("users")
                .document(user.uid)
                .get()
                .await()

            (snapshot.get("categories") as? Map<*, *>)
                ?.mapNotNull { (key, value) ->
                    val name = key as? String
                    val color = value as? String

                    Log.d(
                        "SnapshotCategory",
                        "name=$name, color=$color"
                    )

                    if (name != null && color != null) {
                        CategoryDto(
                            name = name,
                            color = color
                        )
                    } else {
                        null
                    }
                }
                ?: emptyList()

        } else {
            emptyList()
        }
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
            document.toObject(HabitDto::class.java)?.copy(
                id = document.id
            )
        }
    }

    override suspend fun getHabitById(docsId: String): HabitDto? {
        val userId = auth.currentUser?.uid
            ?: throw Exception("User Belum Login")

        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("habits")
            .document(docsId)
            .get()
            .await()

        if (!snapshot.exists()) {
            throw Exception("Habit tidak ditemukan")
        }

        return snapshot.toObject(HabitDto::class.java)

    }
}