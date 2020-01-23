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

    Button btn;
    CheckBox cb;
    Switch sw;
    EditText et;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        view = findViewById(R.id.main_layout);

        btn = (Button)findViewById(R.id.Button);
        cb = (CheckBox)findViewById(R.id.checkBox);
        sw = (Switch)findViewById(R.id.Switch);
        et = (EditText)findViewById(R.id.editText);

        //Checks the checkbox then releases a toast message to user
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb.setChecked(isChecked);
                Toast.makeText(getApplicationContext(),"Here is more information",Toast.LENGTH_LONG).show();

                if(isChecked == true)
                    Snackbar.make(view, "The switch is now on",2000).show();
                if(isChecked == false)
                    Snackbar.make(view, "Off",2000).show();
            }
        });


    }

}
