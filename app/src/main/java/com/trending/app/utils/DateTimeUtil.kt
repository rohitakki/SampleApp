package com.trending.app.utils

import android.text.format.DateUtils
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    fun getTimeAgo(timeString: String): String {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date: Date? = formatter.parse(timeString)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            DateUtils.getRelativeTimeSpanString(calendar.timeInMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString()
        } catch (runTimeException: RuntimeException) {
            timeString
        }
    }
}