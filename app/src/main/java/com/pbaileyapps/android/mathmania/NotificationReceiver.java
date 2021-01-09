package com.pbaileyapps.android.mathmania;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {
    private Context context;
    private AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent i = new Intent(context, NotificationService.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntent);
            Log.d("ALARM_SUCCESS", "Running");
        }
    }


}
