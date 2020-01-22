package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btn;
    CheckBox cb;
    Switch sw;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        btn = (Button)findViewById(R.id.Button);
        cb = (CheckBox)findViewById(R.id.checkBox);
        sw = (Switch)findViewById(R.id.Switch);
        et = (EditText)findViewById(R.id.editText);

    }



}
