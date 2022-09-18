package com.kharin.anotification;

import android.content.Context;

public class ANotificationSettings {
    public Context context;
    public Class<?> aClass;
    public String title;
    public String message;
    public String channelId;
    public String openParameter;
    public int smallIcon;
    public int code;

    public ANotificationSettings(Context activity){
        this.context = activity;
    }

    public ANotificationSettings withTitle(String title) {
        this.title = title;
        return this;
    }

    public ANotificationSettings withMessage(String message) {
        this.message = message;
        return this;
    }

    public ANotificationSettings withChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public ANotificationSettings withCode(int code) {
        this.code = code;
        return this;
    }

    public ANotificationSettings withSmallIcon(int iconKey) {
        if(iconKey < 0) return this;
        smallIcon = iconKey;
        return this;
    }

    public ANotificationSettings withOpenParameter(String openParameter) {
        this.openParameter = openParameter;
        return this;
    }

    @Override
    public String toString() {
        return "NotificationSettings{" +
                "context=" + context +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", channelId='" + channelId + '\'' +
                ", smallIcon=" + smallIcon +
                ", code=" + code +
                ", aClass=" + aClass +
                '}';
    }

    public ANotificationSettings withActivityClass(String aClass) {
        try {
            this.aClass = Class.forName(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
