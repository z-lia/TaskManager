package com.example.taskmanager.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID uuid;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Time mTime;
    private State mState;

    public Task(String mTitle, String mDescription, Date mDate, State mState , Time mTime) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mState = mState;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public Time getTime() {
        return mTime;
    }

    public State getState() {
        return mState;
    }
}
