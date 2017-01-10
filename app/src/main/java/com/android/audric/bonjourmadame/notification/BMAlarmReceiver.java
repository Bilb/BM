package com.android.audric.bonjourmadame.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.ui.activity.LaunchActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by audric on 1/9/17.
 */
public class BMAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "BMAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm received!");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Bonjour Madame");
        mBuilder.setContentText("A new madame is available!");


        Intent resultIntent = new Intent(context, LaunchActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
