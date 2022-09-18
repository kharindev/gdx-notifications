package com.kharin.anotification.alarm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.kharin.anotification.ANotificationManager;
import java.util.Calendar;

public class ANotificationAlarm {
    public Context context;

    public ANotificationAlarm(Context context){
        this.context = context;
    }

    public void scheduledNotification(int notificationId, String title, String message, int hours, int minutes, int seconds, int iconInt, String openParameter, String channelId) {

        Intent notificationIntent = new Intent(context, ANotificationAlarmReceiver.class);
        notificationIntent.putExtra(ANotificationManager.REQUEST_CODE_KEY, notificationId);
        notificationIntent.putExtra(ANotificationManager.TITLE_KEY, title);
        notificationIntent.putExtra(ANotificationManager.MESSAGE_KEY, message);
        notificationIntent.putExtra(ANotificationManager.SMALL_ICON_INT_KEY, iconInt);
        notificationIntent.putExtra(ANotificationManager.CHANNEL_ID_KEY, channelId);
        notificationIntent.putExtra(ANotificationManager.CLASS_KEY, context.getClass().getName());
        notificationIntent.putExtra(ANotificationManager.OPEN_PARAMETER_KEY, openParameter);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);

        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                notificationIntent,
                flag);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        boolean canAlarm = checkAlarmsAccess(am);
        if (am != null && notificationPendingIntent != null && canAlarm) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            } else{
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPendingIntent);
            }
        }
    }

    private boolean checkAlarmsAccess(AlarmManager alarmManager)  {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
            return true;
        }
        return alarmManager.canScheduleExactAlarms();
    }

    public void cancelNotification(int requestCode) {
        Intent intent1 = new Intent(context, ANotificationAlarmReceiver.class);
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent1, flag);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (am != null && pendingIntent != null) {
            am.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
