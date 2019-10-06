package com.example.taskmanager.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.taskmanager.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATEPICKER_DATE = "com.example.taskmanager.DatePicker.date";
    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_date_picker, container, false);
//        mDatePicker = view.findViewById(R.id.date_picker);
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_date_picker, null, false);

        mDatePicker = view.findViewById(R.id.date_picker);

        AlertDialog alertDialog = createAlertDialog(view);

        return alertDialog;
    }


    public AlertDialog createAlertDialog(View view){
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
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

    public void sendResult(){

        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        Date date = new GregorianCalendar(year , month , day).getTime();

        Toast.makeText(getActivity(),year+","+month+","+day,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATEPICKER_DATE,date);
        Fragment fragment = (TaskDialogFragment)getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode() , Activity.RESULT_OK ,intent);
    }
}
