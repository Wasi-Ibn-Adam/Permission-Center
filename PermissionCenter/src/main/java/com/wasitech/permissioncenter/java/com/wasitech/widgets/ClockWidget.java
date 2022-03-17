package com.wasitech.permissioncenter.java.com.wasitech.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Handler;
import android.widget.RemoteViews;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Format;
import com.wasitech.assist.command.family.QA;
import com.wasitech.basics.classes.Issue;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link ClockWidgetConfigureActivity ClockWidgetConfigureActivity}
 */
public class ClockWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        try{
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.clock_widget);
            int m = Calendar.getInstance().get(Calendar.MINUTE);
            int h = Calendar.getInstance().get(Calendar.HOUR);
            int me = Calendar.getInstance().get(Calendar.AM_PM);

            views.setTextViewText(R.id.hour_text, Format.Max2(h) + " ");
            views.setTextViewText(R.id.minute_text, Format.Max2(m) + " ");
            views.setTextViewText(R.id.meradian_text, me == Calendar.AM ? "AM " : "PM ");

            views.setTextViewText(R.id.day_text, QA.day() + " ");
            views.setTextViewText(R.id.date_text, QA.dayN() + ", "+QA.month()+" "+QA.year()+" ");
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        catch (Exception e){
            Issue.print(e, ClockWidget.class.getName());
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final Handler handler = new Handler();
        Timer timer = new Timer();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.post(() -> {
                        // send a broadcast to the widget.
                        updateAppWidget(context, appWidgetManager, appWidgetId);
                    });
                }
            };
            timer.scheduleAtFixedRate(task, 0, 30000);
        }



    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}