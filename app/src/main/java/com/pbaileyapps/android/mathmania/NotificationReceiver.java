package com.pbaileyapps.android.mathmania;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private Context context;
    public static final String channelId = "MATHMANIA_CHANNEL";
    public static final String message = "Daily math reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        buildNotification(context);
    }
    public void buildNotification(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           NotificationChannel channel = new NotificationChannel(channelId,message, NotificationManager.IMPORTANCE_HIGH);
           channel.setDescription("This is the notification channel for Mathmania+");
           manager.createNotificationChannel(channel);
       }
        Notification notification = new NotificationCompat.Builder(context,channelId).setContentTitle("MathMania+")
                .setPriority(NotificationCompat.PRIORITY_HIGH).setContentText("Ready for math?")
                .setSmallIcon(R.drawable.mathmaniaicon).build();
        manager.notify(1,notification);
    }
}
