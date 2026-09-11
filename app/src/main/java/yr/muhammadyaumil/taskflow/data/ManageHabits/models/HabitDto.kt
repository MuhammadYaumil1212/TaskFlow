package yr.muhammadyaumil.taskflow.data.ManageHabits.models

data class HabitDto(
    val id: String = "",
    val name: String = "",
    val notes: String = "",
    val duration: String = "",
    val frequency: String = "",
    val reminder: String = "",
    val categoryHex: String = "",
    val date: Long = 0L,
    val createdAt: Long = System.currentTimeMillis()
)
