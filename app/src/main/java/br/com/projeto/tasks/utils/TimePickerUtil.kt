package br.com.projeto.tasks.utils

class TimePickerUtil {

    companion object {
        fun formatHour(hour: Int): String {
           return  if(hour < 10) "0$hour" else "$hour"
        }

        fun formatMinute(minute: Int): String {
            return if(minute < 10) "0$minute" else "$minute"
        }

        fun formatHourAndMinute(hour: Int, minute: Int): String {
            val hour = if(hour < 10) "0$hour" else "$hour"
            val minute = if(minute < 10) "0$minute" else "$minute"

            return "$hour:$minute"
        }
    }
}