package yr.muhammadyaumil.taskflow.data.addTracker.models

data class HabitDto(
    val id: String = "",
    val name: String = "",
    val notes: String = "",
    val duration: String = "",
    val frequency: String = "",
    val reminder: String = "",
    val categoryHex: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
