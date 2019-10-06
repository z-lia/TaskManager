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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.taskmanager.R;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.taskmanager.controller.DatePickerFragment.EXTRA_DATEPICKER_DATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDialogFragment extends DialogFragment {

    public static final String TAG ="TaskDialogFragment";
    public static final int REQUEST_CODE_DATE_PICKER = 1;
    public static final int REQUEST_CODE_TIME_PICKER = 2;
    private static final String ARG_TASK_DIALOG_TYPE = "taskDialogType";
    private static final String ARG_TASK = "task";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private CheckBox checkBoxDone;

    private Button buttonDate;
    private Button buttonTime;

    private Task mTask;
    private String mTaskDialogType;



    public static TaskDialogFragment newInstance(String taskDialogType ,Task task) {

        Bundle args = new Bundle();
        args.putString( ARG_TASK_DIALOG_TYPE,taskDialogType);
        args.putSerializable(ARG_TASK , task);

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

        setListener();
        mTaskDialogType = getArguments().getString(ARG_TASK_DIALOG_TYPE);
        mTask =(Task) getArguments().getSerializable(ARG_TASK);
        switch (mTaskDialogType){
            case "editTask":
                editTask();
                break;

            case "newTask":
                createNewTask();
                break;

        }


        return createAlertDialog(view );
    }

    private void editTask() {
        editTextTitle.setText(mTask.getTitle());
        editTextDescription.setText(mTask.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = dateFormat.format(  mTask.getDate());
        buttonDate.setText(dateString);
        dateFormat = new SimpleDateFormat("hh : mm");
        dateString = dateFormat.format(mTask.getDate());
        buttonTime.setText(dateString);
        checkBoxDone.setChecked(mTask.getState().equals(State.Done));


    }

    private void createNewTask() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = dateFormat.format(  date);
        buttonDate.setText(dateString);
        buttonTime.setText(date.getHours() + " : " + date.getMinutes());

        mTask = new Task();
        mTask.setDate(date);

    }

    public void setListener(){

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(TaskDialogFragment.this ,REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DatePickerTag");
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(TaskDialogFragment.this ,REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "TimePickerTag");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Log.d(TAG, "onActivityResult: ");
            Date date = (Date)data.getSerializableExtra(EXTRA_DATEPICKER_DATE);
            mTask.setDate(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = dateFormat.format(  date);
            buttonDate.setText(dateString);

        }
        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Log.d(TAG, "onActivityResult: ");

            int hour = data.getIntExtra(TimePickerFragment.EXTRA_TIMEPICKER_HOUR ,new Date().getHours());
            int minuets =data.getIntExtra(TimePickerFragment.EXTRA_TIMEPICKER_MINUETS, new Date().getMinutes());
            Date date = mTask.getDate();
            date.setMinutes(minuets);
            date.setHours(hour);
            mTask.setDate(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String dateString = dateFormat.format(  date);
            buttonTime.setText(dateString);

        }


        }

    public void saveNewTask(Task task) {
        TaskRepository.getInstance().insertTask(task);
    }

    public AlertDialog createAlertDialog(View view ) {

        AlertDialog alertDialog ;//= new AlertDialog(getActivity());
        switch (mTaskDialogType) {
            case "editTask":
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = editTextTitle.getText().toString();
                                String description = editTextDescription.getText().toString();
                                //Date date = new Date();
//                        Time time = new Time();
                                State state;
                                if (checkBoxDone.isChecked())
                                    state = State.Done;
                                else
                                    state = State.ToDo;
                                mTask.setTitle(title);
                                mTask.setDescription(description);
                                mTask.setState(state);
                                //mTask.setDate(date);
                                //Task task = new Task(title, description, date, state, null, TaskRepository.getInstance().getUser());
                                TaskRepository.getInstance().editTask(mTask);

                                Intent intent = new Intent();
                                Fragment fragment = getTargetFragment();
                                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                //((TaskTabPagerFragment)getTargetFragment())


                            }
                        })
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                TaskRepository.getInstance().deleteTask(mTask);
                                Intent intent = new Intent();
                                Fragment fragment = getTargetFragment();
                                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                //dialogInterface.dismiss();

                            }
                        })
                        .setView(view)
                        .create();
                break;

            case "newTask":
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = editTextTitle.getText().toString();
                                String description = editTextDescription.getText().toString();
                                //Date date = new Date();
//                        Time time = new Time();
                                State state;
                                if (checkBoxDone.isChecked())
                                    state = State.Done;
                                else
                                    state = State.ToDo;
                                mTask.setTitle(title);
                                mTask.setDescription(description);
                                mTask.setState(state);
                                //mTask.setDate(date);
                                //Task task = new Task(title, description, date, state, null, TaskRepository.getInstance().getUser());
                                saveNewTask(mTask);

                                Intent intent = new Intent();
                                Fragment fragment = getTargetFragment();
                                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                //((TaskTabPagerFragment)getTargetFragment())


                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        })
                        .setView(view)
                        .create();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mTaskDialogType);
        }
        return alertDialog;
    }


}
