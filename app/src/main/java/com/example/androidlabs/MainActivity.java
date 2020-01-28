package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.*;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    Button login;
    CheckBox cb;
    Switch sw;
    EditText email, password;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.main_layout);

        //Set up the buttons
        login = (Button)findViewById(R.id.Login);;
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


    }

}
