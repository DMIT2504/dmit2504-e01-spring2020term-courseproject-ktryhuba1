package ca.nait.dmit2504.batterystatswidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class BatteryWidget extends AppWidgetProvider {


    IntentFilter batterystats;

    int percent = 0;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            percent = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);


        }

    };


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        batterystats = new IntentFilter();
        batterystats.addAction(Intent.ACTION_BATTERY_CHANGED);

        context.getApplicationContext().registerReceiver(broadcastReceiver,batterystats);

        Intent intent =  new Intent(context, BatteryManager.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.battery_widget);

        views.setProgressBar(R.id.progress_bar_charged_percentage,100,50,false);
        views.setTextViewText(R.id.percent_textview,percent + "SHOW THIS");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        ComponentName thisWidget = new ComponentName(context,BatteryWidget.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int appWidgetId : widgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        broadcastReceiver.onReceive(context,intent);

        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        //context.getApplicationContext().registerReceiver(broadcastReceiver,batterystats);


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

