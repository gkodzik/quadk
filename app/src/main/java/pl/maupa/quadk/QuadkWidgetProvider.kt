package pl.maupa.quadk

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*


class QuadkWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        val format = SimpleDateFormat.getTimeInstance(
            SimpleDateFormat.SHORT, Locale.getDefault()
        )
        val text = format.format(Date())

        for (i in 0 until appWidgetIds!!.size) {

            val widget = RemoteViews(
                context!!.packageName,
                R.layout.quadk_appwidget
            )

            val clickIntent = Intent(context, MainActivity::class.java)
            val clickPI = PendingIntent
                .getActivity(
                    context, 0,
                    clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            widget.setPendingIntentTemplate(R.id.text, clickPI)

            widget.setTextViewText(R.id.time, text)
            widget.setTextViewText(
                R.id.answer, Quadk().getTextIfIsOpen()
            )

            appWidgetManager!!.updateAppWidget(appWidgetIds[i], widget)
        }



        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}