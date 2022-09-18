package com.kharin.anotification.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.kharin.anotification.ANotificationSettings;
import com.kharin.anotification.ANotificationManager;

public class ANotificationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.hasExtra(ANotificationManager.TITLE_KEY) ? intent.getStringExtra(ANotificationManager.TITLE_KEY) : "Title";
        String message = intent.hasExtra(ANotificationManager.MESSAGE_KEY) ? intent.getStringExtra(ANotificationManager.MESSAGE_KEY) : "Message";
        String channelId = intent.hasExtra(ANotificationManager.CHANNEL_ID_KEY) ? intent.getStringExtra(ANotificationManager.CHANNEL_ID_KEY) : "default";
        int code = intent.getIntExtra(ANotificationManager.REQUEST_CODE_KEY, 0);
        int smallIconInt= intent.getIntExtra(ANotificationManager.REQUEST_CODE_KEY, -1);
        String aClass = intent.hasExtra(ANotificationManager.CLASS_KEY) ? intent.getStringExtra(ANotificationManager.CLASS_KEY):"";
        String openParameter = intent.hasExtra(ANotificationManager.OPEN_PARAMETER_KEY) ? intent.getStringExtra(ANotificationManager.OPEN_PARAMETER_KEY):"";

        ANotificationSettings settings = new ANotificationSettings(context)
                .withTitle(title)
                .withMessage(message)
                .withChannelId(channelId)
                .withCode(code)
                .withSmallIcon(smallIconInt)
                .withOpenParameter(openParameter)
                .withActivityClass(aClass);

        showNotification(settings);
    }


    public void showNotification(ANotificationSettings settings)
    {
        Intent intent = new Intent(settings.context, settings.aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ANotificationManager.OPEN_PARAMETER_KEY, settings.openParameter);
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
}

