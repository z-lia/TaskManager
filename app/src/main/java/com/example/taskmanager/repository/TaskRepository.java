package com.example.taskmanager.repository;

import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private List<Task> tasks = new ArrayList<>();

    public static TaskRepository instance;
    private User mUser;

    public void setUser(User mUser) {
        this.mUser = mUser;
    }


    public static TaskRepository getInstance() {
        if (instance == null)
            return new TaskRepository();
        else
            return instance;
    }

    private TaskRepository() {
    }

    public void insertTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void editTask(Task task) {

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskUuid().equals(task.getTaskUuid())) {
                tasks.set(i, task);
            }
        }
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasks(State state) {
        List<Task> tasksOfCurrentUser = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getUserId().equals(mUser.getUserId()) && tasks.get(i).getState().equals(state))
                tasksOfCurrentUser.add(tasks.get(i));
        }
        return tasksOfCurrentUser;
    }

    public User getUser() {
        return mUser;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }
}
