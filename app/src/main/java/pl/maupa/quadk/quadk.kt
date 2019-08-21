package pl.maupa.quadk

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

/**
 * Para - godziny otwarcia i zamknięcia
 */
data class Timeline(val openDate: Date, val closeDate: Date) {
    constructor(open: String, close: String) : this(DateUtils.getDate(open), DateUtils.getDate(close))
}

/**
 * Wyjątek kiedy nie znajdziemy godzin otwarcia.
 */
class NoTimelineException : Exception()

/**
 * Obiekt najjaśniejszej Quadki potrafiący powiedzieć czy jest otwarta
 */
class Quadk {

    companion object {
        /**
         * Sprawdzenie czy w danym momencie Quadka jest otwarta
         *
         * @param: date - sprawdzana data, domyślnie w momencie wywołania
         */
        private fun isOpened(date: Date = Date()): Boolean {
            return try {
                DateUtils.isDateInRange(date, Schedule.getCurrentTimeline())
            } catch (_: NoTimelineException) {
                false
            }
        }

        /**
         * Sprawdza czy Quad-K jest aktualnie otwarta i zwraca słowną odpowiedź.
         */
        fun getTextIfIsOpen(): String {
            return try {
                if (isOpened()) {
                    "TAK!"
                } else {
                    "NIE!"
                }
            } catch (e: NumberFormatException) {
                "Zły format daty"
            }
        }

        /**
         * Sprawdza czy Quad-K jest aktualnie otwarta i zwraca kolor światła - [czerwone|zielone].
         */
        fun getLightColor(): Int {
            return try {
                if (isOpened()) {
                    R.color.light_green
                } else {
                    R.color.light_red
                }
            } catch (e: java.lang.NumberFormatException) {
                Color.RED
            }
        }
    }
}

/**
 * Harmonogram zawierający dane o otwarciu
 */
class Schedule {

    companion object {
        /**
         * Pobranie aktualnego zestawu
         *
         * @throws: NoTimelineException - kiedy nie znajdziemy aktualnych godzin otwarcia
         */
        fun getCurrentTimeline(): Timeline {
            val currentDate = Date()
            for (timeline in openTimeline) {
                if (DateUtils.isDateInRange(currentDate, timeline))
                    return timeline
            }
            throw NoTimelineException()
        }

        /**
         * Aktualna lista otwarcia
         */
        private val openTimeline = listOf(
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
}

/**
 * Proste operacje na datach
 */
class DateUtils {

    companion object {
        /**
         * Date format for easier usage
         */
        val dateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")

        /**
         * Sprawdzenie czy podana data jest z podanym zakresie
         *
         * @return: Boolean
         */
        fun isDateInRange(date: Date, openWindow: Timeline): Boolean {
            return date.after(openWindow.openDate) and date.before(openWindow.closeDate)
        }

        /**
         * Z podanego tekstu sparsuj datę.
         * Format godziny wejściowej - "HH:mm"
         */
        fun getDate(time: String): Date {
            return dateFormat.parse(time)
        }

        /**
         * Pobierz aktualną datę.
         * Format godziny wyjściowej - "HH:mm:ss"
         */
        fun getCurrentDate(): String {
            val format = SimpleDateFormat.getTimeInstance(
                SimpleDateFormat.MEDIUM, Locale.getDefault()
            )
            return format.format(Date())
        }
    }
}