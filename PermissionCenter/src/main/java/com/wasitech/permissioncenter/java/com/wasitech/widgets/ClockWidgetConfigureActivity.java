package com.wasitech.permissioncenter.java.com.wasitech.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * The configuration screen for the {@link ClockWidget ClockWidget} AppWidget.
 */
public class ClockWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public ClockWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // It is the responsibility of the configuration activity to update the app widget

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        ClockWidget.updateAppWidget(this.getApplicationContext(), appWidgetManager, mAppWidgetId);

       // WorkManager manager = WorkManager.getInstance(ClockWidgetConfigureActivity.this);
       // PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(ClockWidgetWork.class, 30, TimeUnit.SECONDS).build();
       // manager.enqueueUniquePeriodicWork("TimeWidget", ExistingPeriodicWorkPolicy.KEEP, request);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}