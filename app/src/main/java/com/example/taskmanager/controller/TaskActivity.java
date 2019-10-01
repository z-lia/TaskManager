package com.example.taskmanager.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.taskfragment_container);
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.taskfragment_container, TaskTabPagerFragment.newInstance())
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TaskActivity.class);
    }
}
