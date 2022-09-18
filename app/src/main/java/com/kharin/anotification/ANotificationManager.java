package com.kharin.anotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.kharin.anotification.alarm.ANotificationAlarm;

public class ANotificationManager {
    public static final String TITLE_KEY ="title_key";
    public static final String MESSAGE_KEY ="message_key";
    public static final String CHANNEL_ID_KEY ="channel_id_key";
    public static final String REQUEST_CODE_KEY ="request_code_key";
    public static final String SMALL_ICON_INT_KEY ="small_icon_int_key";
    public static final String CLASS_KEY ="class_key";
    public static final String OPEN_PARAMETER_KEY ="open_parameter_key";

    private static String channelId = "default";
    private Context context;
    private ANotificationAlarm alarmNotifications;
    public void init(Context context, String id, String name, String description){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.context =context;
            channelId = id;
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        alarmNotifications = new ANotificationAlarm(context);
    }

    public void scheduledNotification(ANotification notification){
        alarmNotifications.scheduledNotification(notification.id, notification.title, notification.message, notification.hours, notification.minutes, notification.seconds, notification.icon, notification.openParameter, channelId);
    }

    public void cancelNotification(ANotification notification){
        cancelNotification(notification.id);
    }

    public void cancelNotification(int code) {
        alarmNotifications.cancelNotification(code);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(code);
    }

    public void showNotification(ANotificationSettings settings)
    {
        Intent intent = new Intent(settings.context, settings.aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(OPEN_PARAMETER_KEY, settings.openParameter);

        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(settings.context, settings.code, intent, flag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(settings.context, settings.channelId)
                .setSmallIcon(settings.smallIcon)
                .setContentTitle(settings.title)
                .setContentText(settings.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(settings.context);
        notificationManager.notify(settings.code, builder.build());
    }

    public String getOpenParameter(){
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            return GetParameter(activity.getIntent(), OPEN_PARAMETER_KEY);
        }
        return "empty";
    }

    public static String GetParameter(Intent intent, String key){
        return intent.hasExtra(key) ? intent.getStringExtra(key) : "empty";
    }
}
