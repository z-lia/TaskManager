package com.example.taskmanager.controller;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.R;
import com.example.taskmanager.repository.TaskRepository;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SureDeleteAllTaskFragment extends DialogFragment {


//    public static final int REQUEST_CODE_DELETE_ALL = 3 ;

    public SureDeleteAllTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sure_delete_all_task, container, false);
    }

    public static final String ARG_CRIME = "crime";



    public static SureDeleteAllTaskFragment newInstance() {

        Bundle args = new Bundle();

        SureDeleteAllTaskFragment fragment = new SureDeleteAllTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater
                .inflate(R.layout.fragment_sure_delete_all_task, null, false);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_all_tasks_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TaskRepository.getInstance().deleteAllTasks();
                        Intent intent = new Intent();

                        TaskListFragment fragment = ((TaskListFragment) getTargetFragment());
                        fragment.onActivityResult(TaskListFragment.REQUEST_CODE_DELETE_ALL, Activity.RESULT_OK ,intent);
                    }
                })
                .setNegativeButton(android.R.string.no , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                )
                .setView(view)
                .create();
    }


}





