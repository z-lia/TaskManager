package com.example.taskmanager.controller;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.taskmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTabPagerFragment extends Fragment {
    private ViewPager viewPager ;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton_add;


    public static TaskTabPagerFragment newInstance() {

        Bundle args = new Bundle();

        TaskTabPagerFragment fragment = new TaskTabPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskTabPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tas_tabk_pager, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        floatingActionButton_add= view.findViewById(R.id.float_button_add);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                String tabTitle="";
                switch (position){
                    case 0:
                        tabTitle= "ToDo";
                        break;
                    case 1:
                        tabTitle= "DOING";
                        break;
                    case 2:
                        tabTitle = "DONE";
                        break;

                }
                return tabTitle;
            }

            @Override
            public Fragment getItem(int position) {

                //recycler view
                return TaskListFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        floatingActionButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialogFragment taskDialogFragment = TaskDialogFragment.newInstance();
                taskDialogFragment.show(getActivity().getSupportFragmentManager() , "Tag");
            }
        });

        return view;
    }



}
