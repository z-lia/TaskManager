package com.example.taskmanager.model;

import com.example.taskmanager.repository.TaskRepository;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private UUID taskUuid;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Time mTime;
    private State mState;



    private UUID userId;

    public Task(){
        taskUuid =UUID.randomUUID();
        userId = TaskRepository.getInstance().getUser().getUserId();
    }
    public Task(String mTitle, String mDescription, Date mDate, State mState , Time mTime ,User user) {
        taskUuid =UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mState = mState;
        userId = user.getUserId();
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setTime(Time time) {
        this.mTime = time;
    }

    public void setState(State mState) {
        this.mState = mState;
    }

    public UUID getUserId() {
        return userId;
    }
    public UUID getTaskUuid() {
        return taskUuid;
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
