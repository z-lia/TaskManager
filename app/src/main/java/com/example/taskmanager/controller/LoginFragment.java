package com.example.taskmanager.controller;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UsersRepository;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText editText_username;
    private EditText editText_password;
    private Button button_signUp;
    private Button button_logIn;

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        button_logIn = view.findViewById(R.id.button_login);
        button_signUp = view.findViewById(R.id.button_signup);
        editText_password = view.findViewById(R.id.edit_text_password_login);
        editText_username = view.findViewById(R.id.edit_text_username_login);

        button_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();
//                 if(username !="" && password!="") {
                if( !username.equals("") && !password.equals("")) {
                    User user = new User(username , password);
                   int result=  UsersRepository.getInstance().searchUserName(user);
                   if(result == -1) {
                       UsersRepository.getInstance().insertUser(user);
                       Intent intent = TaskActivity.newIntent(getActivity());
                       startActivity(intent);
                   }
                   else
                       Snackbar.make(view , "This User has already signed up!" , Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Snackbar.make(view , "User name or password is empty" , Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        button_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();
                //if(username !="" && password!="") {
                if( !username.equals("") && !password.equals("")) {
                    User user = new User(username , password);
                    int result=  UsersRepository.getInstance().searchUserName(user);
                    if(result == 1)
                    {
                        Intent intent = TaskActivity.newIntent(getActivity());
                        startActivity(intent);
                    }
//                        UsersRepository.getInstance().insertUser(user);
                    else if (result == 0)
                        Snackbar.make(view , "This User exist!" , Snackbar.LENGTH_SHORT).show();
                    else{
                        Snackbar.make(view , "The User is not exist! \n Please sign up first." , Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Snackbar.make(view , "User name or password is empty" , Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

}
