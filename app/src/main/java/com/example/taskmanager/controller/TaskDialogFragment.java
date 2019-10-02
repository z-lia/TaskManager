package com.example.taskmanager.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TintTypedArray;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.taskmanager.R;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import java.sql.Time;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDialogFragment extends DialogFragment {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private CheckBox checkBoxDone;

    private Button buttonDate;
    private Button buttonTime;

    private Button buttonCancel;
    private Button buttonSave;


    public static TaskDialogFragment newInstance() {

        Bundle args = new Bundle();

        TaskDialogFragment fragment = new TaskDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater
                .inflate(R.layout.fragment_task_dialog, null, false);

        editTextTitle = view.findViewById(R.id.editText_title);
        editTextDescription = view.findViewById(R.id.editText_description);
        checkBoxDone = view.findViewById(R.id.checkbox_done);
        buttonDate = view.findViewById(R.id.button_date);
        buttonTime = view.findViewById(R.id.button_time);
        Date date = new Date();
        buttonDate.setText(date.getYear() +" /" + (date.getMonth()+1) +"/ "+date.getDate());
        buttonTime.setText(date.getHours()+" : "+date.getMinutes());

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.show(getActivity().getSupportFragmentManager() , "DatePickerTag");
            }
        });





        return createAlertDialog(view);
    }

    public void saveNewTask(Task task){
        TaskRepository.getInstance().insertTask(task);
    }

    public AlertDialog createAlertDialog(View view){
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();
                        Date date = new Date();
//                        Time time = new Time();

                        State state;
                        if(checkBoxDone.isChecked())
                            state =State.Done;
                        else
                            state = State.NotDone;
                        Task task = new Task(title,description, date , state, null );
                        saveNewTask(task);
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


}
