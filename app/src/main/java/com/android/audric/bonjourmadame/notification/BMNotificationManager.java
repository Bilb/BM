package com.android.audric.bonjourmadame.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by audric on 1/9/17.
 */

public class BMNotificationManager {

    private static final String TAG = "BMNotificationManager";


    public static void scheduleBMAlarm(Context context) {
        Intent alarmIntent = new Intent(context, BMAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int hourOfDay = 10, minuteOfHour = 5;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minuteOfHour);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "Alarm BM SET at " + hourOfDay + ":" + minuteOfHour + " !", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Alarm BM SET at " + hourOfDay + ":" + minuteOfHour + " !");
    }
}
