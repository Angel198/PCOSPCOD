package com.jaylax.pcospcod.util;

public class NotificationModel {

    public String id;
    public String msg;
    public String name;

    public NotificationModel(String id, String name, String msg)
    {
        this.id = id;
        this.msg = msg;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }
}
