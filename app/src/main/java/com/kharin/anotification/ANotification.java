package com.kharin.anotification;

public class ANotification {
    public int id;
    public String title;
    public String message;
    public int hours;
    public int minutes;
    public int seconds;
    public String openParameter;
    public int icon;

    public ANotification (int id){
        this.id = id;
    }

    public ANotification withTitle(String title) {
        this.title = title;
        return this;
    }

    public ANotification withMessage(String message) {
        this.message = message;
        return this;
    }

    public ANotification withSmallIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public ANotification withOpenParameter(String openParameter) {
        this.openParameter = openParameter;
        return this;
    }

    public ANotification withHours(int hours) {
        this.hours = hours;
        return this;
    }

    public ANotification withMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public ANotification withSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

}
