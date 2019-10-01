package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private List<Task> tasks = new ArrayList<>();
    public static final TaskRepository instance = new TaskRepository();

    public static TaskRepository getInstance() {
        return instance;
    }

    private TaskRepository (){}

    public void insertTask(Task task){
        tasks.add(task);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public void editTack(Task task){

        for(int i =0; i<tasks.size(); i++){
            if(tasks.get(i).getUuid().equals(task.getUuid())){
                tasks.set(i,task);
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
