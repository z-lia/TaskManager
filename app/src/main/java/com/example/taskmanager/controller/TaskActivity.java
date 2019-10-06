package com.example.taskmanager.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "comp.example.taskmanager.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        User user =(User)getIntent().getSerializableExtra(EXTRA_USER);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.taskfragment_container);
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.taskfragment_container, TaskTabPagerFragment.newInstance(user))
                    .commit();
        }
    }

    public static Intent newIntent(Context context , User user) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }
}
