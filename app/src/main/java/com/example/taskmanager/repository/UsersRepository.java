package com.example.taskmanager.repository;

import com.example.taskmanager.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRepository {
    private List<User> users = new ArrayList<>();
    //    Map<String,String > users = new HashMap<>();

    public static final UsersRepository instance = new UsersRepository();

    public static UsersRepository getInstance() {
        return instance;
    }

    private UsersRepository(){}



    public void insertUser(User user) {
        if (searchUserName(user) == -1)
            users.add(user);
//        users.put(user.getName(),user.getPassword());
    }

    /**
     * @param  user the user of User class that we want to search.
     * @return 1 user and password is correct
     * 0 username is exist but password is incorrect
     * -1 this user is not in the list (both username and pass are incorrect)
     */
    public int searchUserName(User user) {
        int result = -1;
        for (int i = 0; i < users.size(); i++) {
            if (user.getName().equals(users.get(i).getName())) {
                result = 0;
                if (user.getPassword().equals(users.get(i).getPassword())) {
                    return 1;

                }
            }
        }
        return result;
    }

}

