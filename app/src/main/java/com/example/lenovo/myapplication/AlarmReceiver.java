package com.example.lenovo.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationManager nfm;
    Notification ntf;

    @Override
    public void onReceive(Context context, Intent intent) {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();

        Intent myIntent = new Intent(context, MainActivity.class); //SO THIS ACTIVITY IN SETTED ALARM TIME.
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        ntf = new NotificationCompat.Builder(context)
                .setContentTitle("Przypomnienie")
                .setContentText("Pamietaj zrobić zakupy")
                .setTicker("Notification Head")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND) //PLAY DEFAULT SOUND
                .setAutoCancel(true) // REMOVE ALARM NOTIFICATION JUST BY SWIPE
                .setSmallIcon(R.mipmap.ic_launcher) //SHOWED IN STATUS BAR
                .build();

        nfm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nfm.notify(m, ntf);
    }
}
