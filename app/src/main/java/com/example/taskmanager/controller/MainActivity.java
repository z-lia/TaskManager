package com.example.taskmanager.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment==null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, LoginFragment.newInstance())
                    .commit();
    }

   public static Intent newIntent(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        return intent;
   }
}
