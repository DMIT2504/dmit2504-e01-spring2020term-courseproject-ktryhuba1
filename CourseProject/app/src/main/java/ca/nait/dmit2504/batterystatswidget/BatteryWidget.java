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
import android.widget.Switch;


/**
 * Implementation of App Widget functionality.
 */
public class BatteryWidget extends AppWidgetProvider {


    IntentFilter batteryfilter;
    Intent batteryStats;

    int percent = 0;
    int voltage = 0;
    int statusindex = 0;
    int healthindex = 0;
    String status = "";
    String health = "";


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {



        batteryfilter = new IntentFilter();
        batteryfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryStats = context.registerReceiver(null,batteryfilter);


        //context.getApplicationContext().registerReceiver(broadcastReceiver,batterystats);

        //Intent intent =  new Intent(context, BatteryManager.class);
        //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        assert batteryStats != null;
        percent = batteryStats.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        voltage = batteryStats.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        statusindex = batteryStats.getIntExtra(BatteryManager.EXTRA_STATUS,0);
        healthindex = batteryStats.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

        switch(statusindex){
            case 1:
                status = "Unknown";
                break;
            case 2:
                status = "Charging";
                break;
            case 3:
                status = "Discharging";
                break;
            case 4:
                status = "Not Charging";
                break;
            case 5:
                status = "Full";
                break;
            default:
                break;

        }

        switch(healthindex){
            case 1:
                health = "Unknown";
                break;
            case 2:
                health = "Good";
                break;
            case 3:
                health = "OverHeating";
                break;
            case 4:
                health = "Dead";
                break;
            case 5:
                health = "Over Voltage";
                break;
            case 6:
                health = "Unknown Failure";
                break;
            case 7:
                health = "Cold";
                break;
            default:
                break;

        }


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.battery_widget);

        views.setProgressBar(R.id.progress_bar_charged_percentage,100,percent,false);
        views.setTextViewText(R.id.percent_textview,"Life " + percent + "%");
        views.setTextViewText(R.id.voltage_textview, "Energy Use: " + voltage + "ma");
        views.setTextViewText(R.id.status_textview, "Status: "+ status);
        views.setTextViewText(R.id.health_textview,"Battery Health is " + health);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        // There may be multiple widgets active, so update all of them
        ComponentName thisWidget = new ComponentName(context,BatteryWidget.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int appWidgetId : widgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        //intent.putExtra(Intent.ACTION_BATTERY_CHANGED);


        //broadcastReceiver.onReceive(context,intent);

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

