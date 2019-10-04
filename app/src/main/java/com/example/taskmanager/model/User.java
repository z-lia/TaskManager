package com.example.taskmanager.model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
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

    public UUID getUserId() {
        return userId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.userId.equals(((User)obj).getUserId());
    }

}
