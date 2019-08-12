package pl.maupa.quadk

import java.lang.Exception
import java.util.*

/**
 * Para - godziny otwarcia i zamknięcia
 */
data class Timeline(val openDate: Date, val closeDate: Date) {
    constructor(open: String, close: String) : this(DateUtils().dateOf(open), DateUtils().dateOf(close))
}

/**
 * Wyjątek kiedy nie znajdziemy godzin otwarcia.
 */
class NoTimelineException : Exception()

/**
 * Obiekt najjaśniejszej Quadki potrafiący powiedzieć czy jest otwarta
 */
class Quadk {

    /**
     * Sprawdzenie czy w danym momencie Quadka jest otwarta
     *
     * @param: date - sprawdzana data, domyślnie w momencie wywołania
     */
    fun isOpened(date: Date = Date()) : Boolean {
        return try {
            DateUtils().isDateInRange(date, Schedule().getCurrentTimeline())
        } catch ( _ : NoTimelineException) {
            false
        }
    }
}

/**
 * Harmonogram zawierający dane o otwarciu
 */
class Schedule {

    /**
     * Pobranie aktualnego zestawu
     *
     * @throws: NoTimeineException - kiedy nie znajdziemy aktualnych godzin otwarcia
     */
    fun getCurrentTimeline(): Timeline {
        val currentDate = Date()
        for (timeline in openTimeline) {
            if (DateUtils().isDateInRange(currentDate, timeline))
                return timeline
        }
        throw NoTimelineException()
    }

    /**
     * Aktualna lista otwarcia
     */
    val openTimeline = listOf(
        Timeline("00:00", "07:00"),
        Timeline("07:30", "08:00"),
        Timeline("08:30", "09:00"),
        Timeline("09:30", "10:00"),
        Timeline("10:30", "11:00"),
        Timeline("11:30", "12:00"),
        Timeline("12:30", "13:00"),
        Timeline("13:30", "14:00"),
        Timeline("14:30", "15:00"),
        Timeline("15:30", "16:00"),
        Timeline("16:30", "17:00"),
        Timeline("17:30", "18:00"),
        Timeline("18:30", "19:00"),
        Timeline("19:30", "20:30"),
        Timeline("21:00", "22:00"),
        Timeline("22:30", "23:00"),
        Timeline("23:30", "24:00")
    )
}

/**
 * Proste operacje na datach
 */
class DateUtils {
    /**
     * Sprawdzenie czy podana data jest z podanym zakresie
     *
     * @return: Boolean
     */
    fun isDateInRange(date: Date, openWindow: Timeline): Boolean {
        return date.after(openWindow.openDate) and date.before(openWindow.closeDate)
    }

    /**
     * Utworznie daty z wartości tekstowej
     */
    fun dateOf(time: String): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, getHourFromString(time))
        calendar.set(Calendar.MINUTE, getMinutesFromString(time))
        return Date(calendar.timeInMillis)
    }

    /**
     * Parsowanie godzin
     */
    private fun getHourFromString(time: String): Int {
        return time.substring(time.indexOf(":")).toInt()
    }

    /**
     * Parsowanie minut
     */
    private fun getMinutesFromString(time: String): Int {
        return time.substring(time.indexOf(":") + 1, time.length).toInt()
    }
}