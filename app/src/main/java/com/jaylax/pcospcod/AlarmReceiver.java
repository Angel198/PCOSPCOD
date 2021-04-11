 package com.jaylax.pcospcod;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import com.jaylax.pcospcod.R;
import com.jaylax.pcospcod.patientactivities.OngoingTreatmentActivity;
import androidx.appcompat.app.AlertDialog;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

 public class AlarmReceiver extends BroadcastReceiver{

     private static final String CHANNEL_ID = "com.jaylax.pcospcod.channelId";

     @Override
     public void onReceive(Context context, Intent intent) {
         Intent notificationIntent = new Intent(context, OngoingTreatmentActivity.class);
         intent.putExtra("ring","1");

         TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
         stackBuilder.addParentStack(OngoingTreatmentActivity.class);
         stackBuilder.addNextIntent(notificationIntent);

         PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

         Notification.Builder builder = new Notification.Builder(context);

         Notification notification = builder.setContentTitle("PCOS PCOD")
                 .setContentText("Today is your treatment")
                 .setTicker("New Message Alert!")
                 .setSmallIcon(R.drawable.baseline_notifications_24)
                 .setContentIntent(pendingIntent).build();

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             builder.setChannelId(CHANNEL_ID);
         }

         NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             NotificationChannel channel = new NotificationChannel(
                 CHANNEL_ID,
                 "NotificationDemo",
                 IMPORTANCE_DEFAULT
             );
             notificationManager.createNotificationChannel(channel);
         }

         notificationManager.notify(0, notification);

     }

 }
