package com.moutamid.cheffdarbariadmin.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.moutamid.cheffdarbariadmin.MainActivity;
import com.moutamid.cheffdarbariadmin.R;
import com.moutamid.cheffdarbariadmin.activities.NavigationDrawerActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    NotificationManager mNotificationManager;

    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, 0).edit();
        editor.putString("name", s);
        editor.apply();
        Log.d("ContentValues", "onNewToken: " + s);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(2));
        r.play();
        if (Build.VERSION.SDK_INT >= 28) {
            r.setLooping(false);
        }

        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE))
                .vibrate(new long[]{100, 300, 300, 300}, -1);

        int resourceImage = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());
        NotificationCompat.Builder builder = new NotificationCompat.Builder((Context) this, "CHANNEL_IDD");
//        if (Build.VERSION.SDK_INT >= 21) {
//            builder.setSmallIcon(resourceImage);
//        } else {
//            builder.setSmallIcon(resourceImage);
//        }
        builder.setSmallIcon(R.drawable.logoo);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, new Intent(this, NavigationDrawerActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);//134217728
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        builder.setAutoCancel(true);
        builder.setPriority(2);
        this.mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);//"notification"
        if (Build.VERSION.SDK_INT >= 26) {
            this.mNotificationManager.createNotificationChannel(new NotificationChannel("Your_channel_idd", "Channel human readable title", NotificationManager.IMPORTANCE_HIGH));//4
            builder.setChannelId("Your_channel_id");
        }
        this.mNotificationManager.notify(101, builder.build());
    }
}
