package com.example.taskmanager.controller;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {


    public static final String ARGUMENTS_TASK_LIST = "taskList";
    public static final String ARGUMENTS_TAB_NAME = "tabName";
    public static final int REQUEST_CODE_TASK_DIALOG_FRAGMENT = 0;
    public static final int  REQUEST_CODE_DELETE_ALL = 3;

    private TaskAdapter mAdapter;
    private RecyclerView mTaskRecyclerView;
    private ImageView mImageViewEmptyList;
    private FloatingActionButton floatingActionButton_add;

    private List<Task> taskList;
    private State tabState;


    public static TaskListFragment newInstance(State state) {

        Bundle args = new Bundle();

        //args.putSerializable(ARGUMENTS_TASK_LIST, (Serializable) taskList);
        args.putSerializable(ARGUMENTS_TAB_NAME, state);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        setHasOptionsMenu(true);
        floatingActionButton_add = view.findViewById(R.id.float_button_add);
        mImageViewEmptyList = view.findViewById(R.id.imageView_listEmpty);
        mTaskRecyclerView = view.findViewById(R.id.recyclerView_taskList);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // List <Task> taskList = (List) getArguments().getSerializable(ARGUMENTS_TASK_LIST);


        tabState = (State) getArguments().getSerializable(ARGUMENTS_TAB_NAME);
        taskList = TaskRepository.getInstance().getTasks(tabState);

        switch (tabState) {
            case Done:
                mImageViewEmptyList.setImageResource(R.drawable.done_rangi);
                break;
            case ToDo:
                mImageViewEmptyList.setImageResource(R.drawable.todo_rangi);
                break;
            case Doing:
                mImageViewEmptyList.setImageResource(R.drawable.doing_rangi);
                break;
        }

        updateUI(taskList, tabState);

        setListener();

        return view;

    }

    private void setListener() {

        floatingActionButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialogFragment taskDialogFragment = TaskDialogFragment.newInstance("newTask", new Task());
                taskDialogFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_DIALOG_FRAGMENT);
                taskDialogFragment.show(getActivity().getSupportFragmentManager(), "Tag");
            }
        });

    }

    public void updateUI(List taskList, State tabState) {
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(taskList, tabState);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setmTaskList(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUI(TaskRepository.getInstance().getTasks(tabState), tabState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_delete_all_task:
                SureDeleteAllTaskFragment sureDeleteAllTaskFragment = SureDeleteAllTaskFragment.newInstance();
                sureDeleteAllTaskFragment.setTargetFragment(TaskListFragment.this ,REQUEST_CODE_DELETE_ALL);
                sureDeleteAllTaskFragment.show(getFragmentManager(), "Sure delete");
                return false;

            case R.id.menu_item_logout:
                Intent intent = MainActivity.newIntent(getActivity());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewTime;
        private TextView mTextViewIcon;

        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.textView_task_title);
            mTextViewTime = itemView.findViewById(R.id.textView_task_item_date);
            mTextViewIcon = itemView.findViewById(R.id.taskIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TaskDialogFragment taskDialogFragment = TaskDialogFragment.newInstance("editTask", mTask);
                    taskDialogFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_TASK_DIALOG_FRAGMENT);
                    taskDialogFragment.show(getActivity().getSupportFragmentManager(), "Tag");
                    //Intent intent =
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            mTextViewTitle.setText(task.getTitle());
            mTextViewTime.setText(task.getDate().toString());
            mTextViewIcon.setText(task.getTitle().charAt(0) + "");
        }
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTaskList;
        private State mTabState;

        public TaskAdapter(List<Task> mTaskList, State mTabState) {
            this.mTaskList = mTaskList;
            this.mTabState = mTabState;
        }

        public void setmTaskList(List<Task> mTaskList) {
            this.mTaskList = mTaskList;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

            holder.bind(mTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }


}
