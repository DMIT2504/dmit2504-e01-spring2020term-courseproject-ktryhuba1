# dmit2504-e01-spring2020term-courseproject-ktryhuba1
dmit2504-e01-spring2020term-courseproject-ktryhuba1 created by GitHub Classroom

Attempted new material
-Progressbar
-Intent.ACTION_BATTERY_CHANGED
-BatteryManager
-AlarmManager

Completed Implementation
-Progressbar
-Intent.ACTION_BATTERY_CHANGED
-BatteryManager


TUTORIAL

HOW TO CREATE MY BATTERY WIDGET PROJECT

step 1: Create the main_activity as usual, then in the project tab Right-click app then New > Widget > APPWidget

step 2: design your widget how you'd like, I'll be showing how to display a progress bar, but you might have more creative ideas.

step 3: in your widget class, import android.os.BatteryManager, android.content.Intent and android.content.IntentFilter 
and then we will declare an intent and an intentfilter called batterystats and batteryfilter respectively at the top of the class

step 4: in updateAppWidget we will call batteryfilter.addAction(Intent.ACTION_BATTERY_CHANGED) then batterystats = context.registerReceiver(null,batteryfilter)
with that easy set up we now can grab any information we need about our battery in batterystats by using the appropriate .getExtra()

step 5: we can find out what out appropriate getExtra() is by going to https://developer.android.com/reference/android/os/BatteryManager
this will help us know which tag to use so we can get the data we need. 

step 6: for this example we will be using BatteryManager.EXTRA_LEVEL to get the battery level as a percent
so that looks something like: int percent = batteryStats.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);

step 7: now with that we can set up our progressbar to display our battery life. To update your widget views we use RemoteViews
so that looks like:  RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.battery_widget);
                     views.setProgressBar(R.id.progress_bar_charged_percentage,100,percent,false);

step 8: you can now set up your main activity the same way, to collect and save your stats to a local database :)


FAILED step 9: attempt to set up an AlarmManager that will periodicaly access the BatteryManager and save your current stats to your database without having to even turn the app on
- You need to create a class that extends BroadcastReceiver and put your code in the onReceive method.

