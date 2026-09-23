package yr.muhammadyaumil.taskflow.core.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatLongToDateLegacy(
    epochMilli: Long?,
    pattern: String = "d/M/yyyy",
    locale: Locale = Locale("id", "ID")
): String {
    if (epochMilli == null || epochMilli <= 0L) return "-"
    val date = Date(epochMilli)
    val formatter = SimpleDateFormat(pattern, locale)
    return formatter.format(date)
}

fun formatDurationString(timeString: String?): String {
    if (timeString.isNullOrEmpty()) return "-"
    return if (timeString.startsWith("00:")) {
        timeString.removePrefix("00:")
    } else {
        timeString
    }
}