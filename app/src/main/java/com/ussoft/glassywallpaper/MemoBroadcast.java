package com.ussoft.glassywallpaper;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MemoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_Intent = new Intent(context,MainActivity.class);
        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_notification)
                .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo), 128, 128, false))
                .setContentTitle("Glassy Wallpaper")
                .setContentText("it's time to change the wallpaper")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
