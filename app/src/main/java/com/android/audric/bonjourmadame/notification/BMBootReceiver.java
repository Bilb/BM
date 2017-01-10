package com.android.audric.bonjourmadame.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BMBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            BMNotificationManager.scheduleBMAlarm(context);
        }
    }
}