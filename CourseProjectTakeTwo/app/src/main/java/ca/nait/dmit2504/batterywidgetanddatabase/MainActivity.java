package ca.nait.dmit2504.batterywidgetanddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView list;

    SimpleCursorAdapter cursorAdapter;

    saved_stats database;

    Switch autoSaveSwitch;


    TextView status;
    TextView health;
    TextView percent;
    TextView voltage;


    String mStatus = "";
    String mHealth = "";
    int mVoltage;
    int mPercent;

    int timerInmins = 1;


    AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;


    public void getBatteryData(){
        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        Intent batteryStats = registerReceiver(null,ifilter);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,1,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        autoSaveSwitch = findViewById(R.id.auto_save_switch);

        status = findViewById(R.id.status_text);
        health = findViewById(R.id.health_text);
        percent = findViewById(R.id.percent_text);
        voltage = findViewById(R.id.voltage_text);

        getBatteryData();

        status.setText( "Status: "+mStatus);
        health.setText("Health: "+mHealth);
        voltage.setText("Voltage:"+mVoltage+"v");
        percent.setText("Life: "+mPercent+"%");


        list = findViewById(R.id.history_stats_list);

        database = new saved_stats(this);

        Cursor cursor = database.query();
        String[] fromFields = {"Health" , "Status","Voltage","Date"};
        int[] toviews = new int[]{R.id.textView_health,R.id.textView_status,R.id.textView_voltage,R.id.textView_date};

        cursorAdapter = new SimpleCursorAdapter(this,R.layout.custom_listview,cursor,fromFields,toviews);

        list.setAdapter(cursorAdapter);

        int timerInMillis = timerInmins * 1000;

        if(autoSaveSwitch.isChecked()) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + timerInMillis, timerInMillis, pendingIntent);
        }
        else
        {
            alarmManager.cancel(pendingIntent);
        }



    }

    public void SaveNew(View view)
    {


        String mstatus = mStatus;
        String mhealth = mHealth;
        String mvolts = String.valueOf(mVoltage);

        database.SaveNewData(mstatus,mvolts,mhealth);


    }





}
