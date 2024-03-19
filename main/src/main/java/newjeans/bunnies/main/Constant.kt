package newjeans.bunnies.main

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Constant {
    private val postDatePattern = DateTimeFormatter.ofPattern("MM/dd - HH:mm")

    fun postDatePattern(originalDateTime: String): String {
        val dateTime = LocalDateTime.parse(originalDateTime)
        return dateTime.format(postDatePattern)
    }
}