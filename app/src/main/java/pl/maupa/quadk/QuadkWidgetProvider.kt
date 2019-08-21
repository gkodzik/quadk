package pl.maupa.quadk

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


class QuadkWidgetProvider : AppWidgetProvider() {

    private val WIDGET_REFRESH: String = "pl.maupa.quadk.WIDGET_REFRESH"

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        val remoteViews = RemoteViews(context!!.packageName, R.layout.quadk_appwidget)
        val widget = ComponentName(context, QuadkWidgetProvider::class.java)

        remoteViews.setTextViewText(R.id.time, DateUtils.getCurrentDate())
        remoteViews.setTextViewText(R.id.answer, Quadk.getTextIfIsOpen())
        remoteViews.setInt(R.id.widget, "setBackgroundResource", Quadk.getLightColor())

        remoteViews.setOnClickPendingIntent(R.id.widget, getPendingSelfIntent(context))
        appWidgetManager!!.updateAppWidget(widget, remoteViews)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    override fun onReceive(context: Context?, intent: Intent?) {

        super.onReceive(context, intent)

        if (WIDGET_REFRESH == intent!!.action) {

            val appWidgetManager = AppWidgetManager.getInstance(context)

            val remoteViews = RemoteViews(context!!.packageName, R.layout.quadk_appwidget)
            val widget = ComponentName(context, QuadkWidgetProvider::class.java)

            remoteViews.setTextViewText(R.id.time, DateUtils.getCurrentDate())
            remoteViews.setTextViewText(R.id.answer, Quadk.getTextIfIsOpen())
            remoteViews.setInt(R.id.widget, "setBackgroundResource", Quadk.getLightColor())

            appWidgetManager.updateAppWidget(widget, remoteViews)
        }

        super.onReceive(context, intent)
    }

    private fun getPendingSelfIntent(context: Context): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = WIDGET_REFRESH
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}