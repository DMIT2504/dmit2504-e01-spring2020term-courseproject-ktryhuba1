package ca.nait.dmit2504.batterywidgetanddatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    saved_stats database;

    String mStatus = "";
    String mHealth = "";
    int mVoltage;
    int mPercent;

    @Override
    public void onReceive(Context context, Intent intent) {

        getBatteryData(context);
        database = new saved_stats(context);
        database.SaveNewData(mStatus,String.valueOf(mVoltage),mHealth);


    }

    public void getBatteryData(Context context){
        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStats = context.registerReceiver(null,ifilter);
        assert batteryStats != null;
        mPercent = batteryStats.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        mVoltage = batteryStats.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        int statusindex = batteryStats.getIntExtra(BatteryManager.EXTRA_STATUS,0);
        int healthindex = batteryStats.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

        switch(statusindex){
            case 1:
                mStatus = "Unknown";
                break;
            case 2:
                mStatus = "Charging";
                break;
            case 3:
                mStatus = "Discharging";
                break;
            case 4:
                mStatus = "Not Charging";
                break;
            case 5:
                mStatus = "Full";
                break;
            default:
                break;

        }

        switch(healthindex){
            case 1:
                mHealth = "Unknown";
                break;
            case 2:
                mHealth = "Good";
                break;
            case 3:
                mHealth = "OverHeating";
                break;
            case 4:
                mHealth = "Dead";
                break;
            case 5:
                mHealth = "Over Voltage";
                break;
            case 6:
                mHealth = "Unknown Failure";
                break;
            case 7:
                mHealth = "Cold";
                break;
            default:
                break;

        }

    }




}
