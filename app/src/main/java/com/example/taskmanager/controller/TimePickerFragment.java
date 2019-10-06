package com.example.taskmanager.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taskmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIMEPICKER_HOUR = "com.example.taskmanager.timepicker.hour";
    public static final String EXTRA_TIMEPICKER_MINUETS = "com.example.taskmanager.timepicker.minuets";
    private TimePicker mTimePicker;

    public TimePickerFragment() {
        // Required empty public constructor
    }


    public static TimePickerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_time_picker, null, false);
        mTimePicker = view.findViewById(R.id.time_picker);

        AlertDialog alertDialog = createAlertDialog(view);
        //return super.onCreateDialog(savedInstanceState);
        return alertDialog;
    }
    public AlertDialog createAlertDialog(View view){
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("Change Time", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                })
                .setView(view)
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendResult(){

        mTimePicker.setIs24HourView(true);
        int hour = mTimePicker.getHour();
        int minuets = mTimePicker.getMinute();

        Toast.makeText(getActivity(),hour+":"+"minuets",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIMEPICKER_HOUR,hour);
        intent.putExtra(EXTRA_TIMEPICKER_MINUETS,minuets);
        Fragment fragment = (TaskDialogFragment)getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode() , Activity.RESULT_OK ,intent);
    }

}
