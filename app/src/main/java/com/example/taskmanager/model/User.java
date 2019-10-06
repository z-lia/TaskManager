package com.example.taskmanager.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    public UUID getUserId() {
        return userId;
    }

    private UUID userId;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        userId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
