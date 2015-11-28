package com.example.marek.todaylist.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by marek on 01/11/15.
 */
public class Task extends RealmObject{
    private String name;
    private String description;
    private int state;
    private long timeStamp;

    public Task(){
    }

    public Task(String name, String description, int state) {
        this.timeStamp = System.currentTimeMillis();
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
